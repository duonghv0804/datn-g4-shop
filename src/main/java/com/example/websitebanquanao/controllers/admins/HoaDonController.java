package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.*;
import com.example.websitebanquanao.infrastructures.requests.NhanVienRequest;
import com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse;
import com.example.websitebanquanao.infrastructures.responses.BanHangTaiQuayResponse;
import com.example.websitebanquanao.infrastructures.responses.GioHangResponse;
import com.example.websitebanquanao.infrastructures.responses.SanPhamChiTietResponse;
import com.example.websitebanquanao.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/admin/hoa-don")
    public String index(Model model,@RequestParam(name = "page", defaultValue = "1") int page) {
        model.addAttribute("view", "/views/admin/hoa-don/quan-li-hoa-don.jsp");
        model.addAttribute("listHoaDon", hoaDonService.getPage(page, 10));
        return "admin/layout";
    }

    @GetMapping("/view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable("id") UUID id, Model model) {
        // Lấy thông tin hoá đơn chi tiết dựa trên id hoá đơn
        HoaDon hoaDon = hoaDonService.getById(id);

        // Lấy danh sách sản phẩm trong giỏ hàng (hoặc hoá đơn chi tiết)
        // Và set vào model để hiển thị trên trang JSP
        List<GioHangResponse> listSanPhamTrongGioHang = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);

        // Truyền idHoaDon để sử dụng trong form
        List<BanHangTaiQuayResponse> listProduct = sanPhamChiTietService.findAllCtsp();
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listHoaDon", hoaDonService.getAll());
        model.addAttribute("idHoaDon", id);
        model.addAttribute("hoaDon", hoaDon); // Truyền giá trị hoaDon vào model

        model.addAttribute("listSanPhamTrongGioHang", listSanPhamTrongGioHang);

        model.addAttribute("view", "/views/admin/hoa-don/quan-li-hoa-don.jsp");
        return "admin/layout";
    }

    @GetMapping("/admin/hoa-don/{id}")
    public String viewHoaDonAdmin(@PathVariable("id") UUID id, Model model) {
        // Lấy thông tin hoá đơn chi tiết dựa trên id hoá đơn
        HoaDon hoaDon = hoaDonService.getById(id);
        List<GioHangResponse> listSanPhamTrongGioHang = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);
        List<BanHangTaiQuayResponse> listProduct = sanPhamChiTietService.findAllCtsp();
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listHoaDon", hoaDonService.getAll());
        model.addAttribute("idHoaDon", id);
        model.addAttribute("hoaDon", hoaDon); // Truyền giá trị hoaDon vào model
        model.addAttribute("listSanPhamTrongGioHang", listSanPhamTrongGioHang);
        model.addAttribute("view", "/views/admin/hoa-don/danh-sach-hoa-don.jsp");
        return "admin/layout";
    }


    @GetMapping("/admin/hoa-don/filter")
    public String filterHoaDon(@RequestParam("trangThai") Integer trangThai, Model model, @RequestParam("checkTH") Boolean checkTH) {
        List<HoaDon> filteredHoaDon = hoaDonService.getHoaDonByTrangThai(trangThai, checkTH);
        model.addAttribute("listHoaDon", filteredHoaDon);
        model.addAttribute("view", "/views/admin/hoa-don/quan-li-hoa-don.jsp");
        return "admin/layout";
    }

    @PostMapping("/admin/hoa-don/update-trang-thai/{id}")
    public String updateTrangThaiHoaDon(@PathVariable("id") UUID id, @RequestParam("trangThai") Integer trangThai, @RequestParam("ghiChu") String ghiChu, Model model, RedirectAttributes redirectAttributes) {
        NhanVien nhanVien = new NhanVien();
        NhanVienRequest nhanVienRequest = (NhanVienRequest) httpSession.getAttribute("admin");
        nhanVien.setId(nhanVienRequest.getId());
        HoaDon hoaDon = hoaDonService.getById(id);
        hoaDon.setIdNhanVien(nhanVien);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTrangThai(trangThai);
        hoaDonService.update(hoaDon, id);
        redirectAttributes.addFlashAttribute("successMessage", "hóa đơn đã được hủy");
        model.addAttribute("view", "/views/admin/hoa-don/quan-li-hoa-don.jsp");
        return "redirect:/admin/hoa-don/" + id;
    }

    @PostMapping("/admin/hoa-don/update-trang-thai-online/{id}")
    public String updateTrangThaiHoaDonOnline(@PathVariable("id") UUID id,
                                              @RequestParam("trangThai") Integer trangThai,
                                              @RequestParam("ghiChu") String ghiChu,
                                              @RequestParam("maVanChuyen") String maVanChuyen,
                                              @RequestParam("tenDonViVanChuyen") String tenDonViVanChuyen,
                                              @RequestParam("phiVanChuyen") String phiVanChuyen
            , Model model, RedirectAttributes redirectAttributes) {
        String phi2 = phiVanChuyen.replace(".", "");
        if (trangThai == 4) {
            List<GioHangResponse> listSanPhamTrongGioHang = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);
            for (GioHangResponse gh : listSanPhamTrongGioHang) {
                SanPhamChiTiet ctsp = sanPhamChiTietService.findById(gh.getIdSanPhamChiTiet());
                if (ctsp.getSoLuong() == 0) {
                    redirectAttributes.addFlashAttribute("errorMessage", "hóa đơn xác nhận không thành công do số lượng sản phẩm trong kho không đủ");
                    return "redirect:/admin/hoa-don/" + id;
                }
            }
            for (GioHangResponse gh : listSanPhamTrongGioHang) {
                SanPhamChiTiet ctsp = sanPhamChiTietService.findById(gh.getIdSanPhamChiTiet());
                HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.getHoaDonChiTietByHoaDonIdAndIdSanPhamChiTiet(id, ctsp.getId());
                hoaDonChiTiet.setTrangThai(3);
                hoaDonChiTietService.update(hoaDonChiTiet);
                int soLuongConLai = ctsp.getSoLuong() - Integer.valueOf(String.valueOf(gh.getSoLuong()));
                ctsp.setSoLuong(soLuongConLai);
                sanPhamChiTietService.updateSoLuong(ctsp);
            }
        }
        NhanVien nhanVien = new NhanVien();
        NhanVienRequest nhanVienRequest = (NhanVienRequest) httpSession.getAttribute("admin");
        nhanVien.setId(nhanVienRequest.getId());
        HoaDon hoaDon = hoaDonService.getById(id);
        hoaDon.setIdNhanVien(nhanVien);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTrangThai(trangThai);
        hoaDon.setMaVanChuyen(maVanChuyen);
        hoaDon.setTenDonViVanChuyen(tenDonViVanChuyen);
        hoaDon.setPhiVanChuyen(BigDecimal.valueOf(Float.valueOf(phi2)));
        hoaDonService.update(hoaDon, id);
        model.addAttribute("view", "/views/admin/hoa-don/quan-li-hoa-don.jsp");
        return "redirect:/admin/hoa-don/" + id;
    }

    @PostMapping("/admin/hoa-don/xac-nhan-thanh-toan/{id}")
    public String xacNhanThanhToan(@PathVariable("id") UUID id, @RequestParam("trangThai") Integer trangThai, @RequestParam("ghiChu") String ghiChu
            , RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonService.getById(id);
        hoaDon.setGhiChu(ghiChu);
        Instant instant = Instant.now();
        hoaDon.setNgayNhan(instant);
        hoaDon.setNgayThanhToan(instant);
        hoaDon.setTrangThai(trangThai);
        Integer tienGiam1 = 0;
        if (hoaDon.getTienGiam() != null) {
            tienGiam1 = hoaDon.getTienGiam().intValue();
        }
        BigDecimal thanhToan = BigDecimal.valueOf(hoaDon.getTongTien().intValue() + hoaDon.getPhiVanChuyen().intValue() - tienGiam1);
        hoaDon.setThanhToan(thanhToan);
        hoaDonService.update(hoaDon, id);
        redirectAttributes.addFlashAttribute("successMessage", "hóa đơn đã thành công");
        return "redirect:/admin/hoa-don/" + id;
    }


}
