package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.infrastructures.responses.*;
import com.example.websitebanquanao.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private KhachHangService KhachHangService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private AnhSanPhamService anhSanPhamService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    private KhuyenMaiChiTietService khuyenMaiChiTietService;
    @Autowired
    private EmailService emailService;
    @Autowired
    HttpSession session;

    // getlist sản phẩm by id giỏ hàng
    @GetMapping("/view/spct/view/{id}")
    public List<GioHangResponse> viewHoaDon(@PathVariable("id") UUID id) {
        return hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);
    }

    @GetMapping("/view/thong-tin-khach-hang")
    public List<KhachHangResponse> viewKhachHang() {
        return KhachHangService.getAll();
    }

    @GetMapping("/view/san-pham-chi-tiet")
    public List<SanPhamChiTietResponse> viewSanPhamChiTiet() {
        List<SanPhamChiTietResponse> listProduct = sanPhamChiTietService.getAll();
        return listProduct;
    }

    @GetMapping("/phan-tram-giam/{idSanPham}")
    @ResponseBody
    public ResponseEntity<Integer> soPhanTramGiamKhuyenMai(@PathVariable("idSanPham") UUID idSanPham) {
        return ResponseEntity.ok(khuyenMaiChiTietService.getSoPhanTramGiamByIdSanPhamChiTiet(idSanPham));
    }

    @GetMapping("/get-anh-san-pham/{idCtsp}")
    @ResponseBody
    public List<AnhSanPhamResponse> getAnhSanPhamByCtspId(@PathVariable("idCtsp") UUID idCtsp) {
        List<AnhSanPhamResponse> listAnh = anhSanPhamService.getListAnhByIdSanPham(idCtsp);
        return listAnh;
    }

    // filter theo size hoặc color hoặc search term
    @GetMapping("/filter-products")
    public List<BanHangTaiQuayResponse> filterProducts(@RequestParam(name = "size", required = false) String size, @RequestParam(name = "color", required = false) String color, @RequestParam(name = "searchTerm", required = false) String searchTerm) {
        return sanPhamChiTietService.filterProducts(size, color, searchTerm);
    }

    @GetMapping("/topbanchay")
    public List<Object> top5SanPhamBanChay() {
        return hoaDonChiTietService.top5SanPhamBanChay();
    }

    @GetMapping("/topbancham")
    public List<Object> top5SanPhamBanCham() {
        return hoaDonChiTietService.top5SanPhamBanCham();
    }


    @PostMapping("/send")
    public String sendEmail(@RequestParam(name = "to", defaultValue = "xmenrasdra14022003@gmail.com") String to, @RequestParam(name = "subject", defaultValue = "Cảm ơn bạn đã mua hàng") String subject, @RequestParam(name = "body", defaultValue = "Đây là hóa đơn của bạn") String body, @RequestParam(name = "maHD", defaultValue = "HD0003") String maHD) {
        try {
            emailService.sendEmail(to, subject, body, maHD);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }
    }

    @GetMapping("/so-luong-san-pham/{idSanPham}/{idMauSac}/{idKichCo}")
    public ResponseEntity<Integer> getSoLuongSanPham(@PathVariable("idSanPham") UUID idSanPham, @PathVariable("idMauSac") Integer idMauSac, @PathVariable("idKichCo") Integer idKichCo) {
        return ResponseEntity.ok(sanPhamChiTietService.getSoLuongSanPham(idSanPham, idMauSac, idKichCo));
    }

    @GetMapping("/soLuong/{idSanPhamChiTiet}")
    public ResponseEntity<Integer> getSoLuongTrongKho(@PathVariable UUID idSanPhamChiTiet) {
        try {
            Integer soLuong = sanPhamChiTietService.getSoLuongSanPhamChiTietByIdSanPham(idSanPhamChiTiet);
            session.setAttribute("daHoan_" + idSanPhamChiTiet, true);
            return ResponseEntity.ok(soLuong);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(0);
    }

}

