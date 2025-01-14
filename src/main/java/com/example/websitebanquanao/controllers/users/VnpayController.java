package com.example.websitebanquanao.controllers.users;

import com.example.websitebanquanao.infrastructures.requests.KhachHangRequest;
import com.example.websitebanquanao.infrastructures.responses.HoaDonChiTietUserResponse;
import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import com.example.websitebanquanao.services.HoaDonService;
import com.example.websitebanquanao.services.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Controller
public class VnpayController {

    @Autowired
    private VnpayService vnPayService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HttpSession session;
    @Autowired
    private KhachHangRequest khachHangRequest;

    @PostMapping("/submit-payment/{id}")
    public String submitPayment(@PathVariable("id") UUID id, HttpServletRequest request) {
        HoaDonChiTietUserResponse hoaDonChiTietUserResponse = hoaDonService.findHoaDonUserResponseById(id);
        BigDecimal tongTien = hoaDonService.sumTongTienByIdHoaDon(id);

        // xoá dấu tiếng việt
        String result = java.text.Normalizer.normalize(hoaDonChiTietUserResponse.getNguoiNhan(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String finalString = result.replaceAll(" ", " ");

        String orderInfo = "Thanh toan don hang " + hoaDonChiTietUserResponse.getMa() + " cua " + finalString;
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(hoaDonChiTietUserResponse.getMa(), tongTien, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }


    @GetMapping("/vnpay-payment")
    public String check(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        String maHoaDon = request.getParameter("vnp_TxnRef");
        model.addAttribute("maHoaDon", maHoaDon);
        model.addAttribute("kh", khachHangRequest);
        if (paymentStatus == 1) {
            Instant instant = Instant.now();
            hoaDonService.updateNgayThanhToanByIdHoaDon(maHoaDon, instant);
            model.addAttribute("viewContent", "/views/user/thanh-toan-thanh-cong.jsp");
            return "user/layout";
        } else {
            model.addAttribute("viewContent", "/views/user/thanh-toan-that-bai.jsp");
            return "user/layout";
        }
    }
}
