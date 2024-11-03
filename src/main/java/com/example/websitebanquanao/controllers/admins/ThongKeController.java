package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.services.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
@Controller
@RequestMapping("/admin/thong-ke")
public class ThongKeController {

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    private static final String redirect = "redirect:/admin";
    @RequestMapping("/index")
    public String hienThiTongGia(Model model) {

        //Doanh thu :

        Double TongDoanhThu = hoaDonChiTietService.TongDoanhThu();

        Double TongDoanhThuNgayHienTai = hoaDonChiTietService.TongDoanhThuNgayHienTai();

        Double TongDoanhThuThangHienTai = hoaDonChiTietService.TongDoanhThuThangHienTai();

        Double TongDoanhThuThangHienTaiTru1 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru1();

        Double TongDoanhThuThangHienTaiTru2 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru2();

        Double TongDoanhThuThangHienTaiTru3 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru3();

        Double TongDoanhThuThangHienTaiTru4 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru4();

        Double TongDoanhThuThangHienTaiTru5 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru5();

        Double TongDoanhThuThangHienTaiTru6 = hoaDonChiTietService.TongDoanhThuThangHienTaiTru6();

        Double TongDoanhThuTuanHienTai = hoaDonChiTietService.TongDoanhThuTuanHienTai();

        Double TongDoanhThuNamHienTai = hoaDonChiTietService.TongDoanhThuNamHienTai();

        Double TongDoanhThu6ThangQua = hoaDonChiTietService.TongDoanhThu6ThangQua();

        Double TongDoanhThu1NamQua = hoaDonChiTietService.TongDoanhThu1NamQua();

//        //Ten nhan Vien :
//
//        String NhanVienBanDcNhieuNhat = hoaDonChiTietService.NhanVienBanDcNhieuNhat();
//
//        //Ten san pham :
//
//        String SanPhamBanChayNhatTrongNgay = hoaDonChiTietService.SanPhamBanChayNhatTrongNgay();
//
//        String SanPhamBanChayNhatTrongThang = hoaDonChiTietService.SanPhamBanChayNhatTrongThang();
//
//        String SanPhamBanChayNhatTrongTuan = hoaDonChiTietService.SanPhamBanChayNhatTrongTuan();


        //Lay ra thang nam trc :
        String Tru0ThangTruoc = hoaDonChiTietService.Tru0ThangTruoc();

        String Tru1ThangTruoc = hoaDonChiTietService.Tru1ThangTruoc();

        String Tru2ThangTruoc = hoaDonChiTietService.Tru2ThangTruoc();

        String Tru3ThangTruoc = hoaDonChiTietService.Tru3ThangTruoc();

        String Tru4ThangTruoc = hoaDonChiTietService.Tru4ThangTruoc();

        String Tru5ThangTruoc = hoaDonChiTietService.Tru5ThangTruoc();

        String Tru6ThangTruoc = hoaDonChiTietService.Tru6ThangTruoc();

        //Check null doanh thu :

        if (TongDoanhThu == null) {
            TongDoanhThu = 0.0;
        }
        if (TongDoanhThuNgayHienTai == null) {
            TongDoanhThuNgayHienTai = 0.0;
        }
        if (TongDoanhThuThangHienTai == null) {
            TongDoanhThuThangHienTai = 0.0;
        }
        if (TongDoanhThuTuanHienTai == null) {
            TongDoanhThuTuanHienTai = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru1 == null) {
            TongDoanhThuThangHienTaiTru1 = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru2 == null) {
            TongDoanhThuThangHienTaiTru2 = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru3 == null) {
            TongDoanhThuThangHienTaiTru3 = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru4 == null) {
            TongDoanhThuThangHienTaiTru4 = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru5 == null) {
            TongDoanhThuThangHienTaiTru5 = 0.0;
        }
        if (TongDoanhThuThangHienTaiTru6 == null) {
            TongDoanhThuThangHienTaiTru6 = 0.0;
        }
        if (TongDoanhThuNamHienTai == null) {
            TongDoanhThuNamHienTai = 0.0;
        }
        if (TongDoanhThu6ThangQua == null) {
            TongDoanhThu6ThangQua = 0.0;
        }
        if (TongDoanhThu1NamQua == null) {
            TongDoanhThu1NamQua = 0.0;
        }

        //Doanh thu :
        model.addAttribute("TongDoanhThu", TongDoanhThu);

        model.addAttribute("TongDoanhThuNgayHienTai", TongDoanhThuNgayHienTai);

        model.addAttribute("TongDoanhThuTuanHienTai", TongDoanhThuTuanHienTai);

        model.addAttribute("TongDoanhThuThangHienTai", TongDoanhThuThangHienTai);

        model.addAttribute("TongDoanhThuThangHienTaiTru1", TongDoanhThuThangHienTaiTru1);

        model.addAttribute("TongDoanhThuThangHienTaiTru2", TongDoanhThuThangHienTaiTru2);

        model.addAttribute("TongDoanhThuThangHienTaiTru3", TongDoanhThuThangHienTaiTru3);

        model.addAttribute("TongDoanhThuThangHienTaiTru4", TongDoanhThuThangHienTaiTru4);

        model.addAttribute("TongDoanhThuThangHienTaiTru5", TongDoanhThuThangHienTaiTru5);

        model.addAttribute("TongDoanhThuThangHienTaiTru6", TongDoanhThuThangHienTaiTru6);

        model.addAttribute("TongDoanhThuNamHienTai", TongDoanhThuNamHienTai);

        model.addAttribute("TongDoanhThu6ThangQua", TongDoanhThu6ThangQua);

        model.addAttribute("TongDoanhThu1NamQua", TongDoanhThu1NamQua);

        //Thang nam trc :

        model.addAttribute("Tru0ThangTruoc", Tru0ThangTruoc);

        model.addAttribute("Tru1ThangTruoc", Tru1ThangTruoc);

        model.addAttribute("Tru2ThangTruoc", Tru2ThangTruoc);

        model.addAttribute("Tru3ThangTruoc", Tru3ThangTruoc);

        model.addAttribute("Tru4ThangTruoc", Tru4ThangTruoc);

        model.addAttribute("Tru5ThangTruoc", Tru5ThangTruoc);

        model.addAttribute("Tru6ThangTruoc", Tru6ThangTruoc);
        // anhplq

        // tổng sản phẩm đã bán
        model.addAttribute("tongSanPhamDaBan", hoaDonChiTietService.TongSanPhamDaBan());
        // tổng đơn hàng đã bán
        model.addAttribute("tongDonHangDaBan", hoaDonChiTietService.TongDonHang());
        // tổng khách hàng đã mua
        model.addAttribute("tongKhachHangDaMua", hoaDonChiTietService.TongKhachHang());

        Object[] nhanVienInfo = (Object[]) hoaDonChiTietService.NhanVienBanDuocNhieuSanPhamNhat();

        if (nhanVienInfo != null) {
            String tenNhanVien = (String) nhanVienInfo[0];
            Integer soLuongSanPham = (Integer) nhanVienInfo[1];
            BigDecimal doanhThu = (BigDecimal) nhanVienInfo[2];
            model.addAttribute("tenNhanVien", tenNhanVien);
            model.addAttribute("soLuong", soLuongSanPham);
            model.addAttribute("tongTien", doanhThu);

            // top 1 sản phẩm bán chạy nhất
            Object[] sanPhamInfo = (Object[]) hoaDonChiTietService.SanPhamBanChayNhat();
            String tenSanPham = (String) sanPhamInfo[0];
            Integer soLuong = (Integer) sanPhamInfo[1];
            model.addAttribute("tenSanPham", tenSanPham);
            model.addAttribute("soLuongSanPham", soLuong);

            model.addAttribute("view", "/views/admin/thong-ke.jsp");
            return "admin/layout";

        } else {
            return redirect;
        }
}
}


