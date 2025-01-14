package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.HoaDon;
import com.example.websitebanquanao.entities.HoaDonChiTiet;
import com.example.websitebanquanao.entities.SanPhamChiTiet;
import com.example.websitebanquanao.infrastructures.responses.GioHangResponse;
import com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse;
import com.example.websitebanquanao.infrastructures.responses.HoaDonUserResponse;
import com.example.websitebanquanao.repositories.HoaDonChiTietRepository;
import com.example.websitebanquanao.repositories.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;


    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private CreatePDF createPDF;

    // admin
    public void add(HoaDonChiTiet hoaDonChiTiet) {
        java.util.Date currentDate = new java.util.Date();
        hoaDonChiTiet.setNgaySua(new Date(currentDate.getTime()));
        hoaDonChiTiet.setNgayTao(new Date(currentDate.getTime()));
        hoaDonChiTiet.setTrangThai(3);
        hoaDonChiTietRepository.save(hoaDonChiTiet);
        System.out.println(hoaDonChiTiet.getId());
    }

    public List<GioHangResponse> getHoaDonChiTietByHoaDonId(UUID hoaDonId) {
        return hoaDonChiTietRepository.findTotalQuantityByHoaDonId(hoaDonId);
    }

    public HoaDonChiTiet getHoaDonChiTietByHoaDonIdAndIdSanPhamChiTiet(UUID hoaDonId, UUID idSanPhamChiTiet) {
        return hoaDonChiTietRepository.findByHoaDonIdAndSanPhamChiTietId(hoaDonId, idSanPhamChiTiet);
    }

    public HoaDonChiTiet getById(UUID id) {
        if (hoaDonChiTietRepository.findById(id).isPresent()) {
            return hoaDonChiTietRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(UUID idHoaDonChiTiet) {
        hoaDonChiTietRepository.deleteById(idHoaDonChiTiet);
    }


    public void update(HoaDonChiTiet hoaDonChiTiet) {
        if (hoaDonChiTiet != null && hoaDonChiTiet.getId() != null) {
            java.util.Date currentDate = new java.util.Date();
            hoaDonChiTiet.setNgaySua(new Date(currentDate.getTime()));
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }
    }

    public HoaDonChiTiet getChiTietByHoaDonAndSanPham(UUID idHoaDon, UUID idSanPhamChiTiet) {
        return hoaDonChiTietRepository.findByHoaDonIdAndSanPhamChiTietId(idHoaDon, idSanPhamChiTiet);
    }

    // user
    public void addHoaDonChiTietUser(HoaDon hoaDon, UUID idKhachHang) {
        List<GioHangUserResponse> listSanPhamGioHang = gioHangChiTietService.getListByIdKhachHang(idKhachHang);

        for (GioHangUserResponse gioHangUserResponse : listSanPhamGioHang) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(gioHangUserResponse.getIdSanPhamChiTiet());
            hoaDonChiTiet.setIdSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setSoLuong(gioHangUserResponse.getSoLuong());
            hoaDonChiTiet.setGia(gioHangUserResponse.getGia());
            java.util.Date currentDate = new java.util.Date();
            hoaDonChiTiet.setNgaySua(new Date(currentDate.getTime()));
            hoaDonChiTiet.setNgayTao(new Date(currentDate.getTime()));
            hoaDonChiTiet.setTrangThai(1);
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }
        createPDF.exportPDFBill(hoaDon, listSanPhamGioHang, sumTongTienByIdHoaDon(hoaDon.getId()).toString());
        gioHangChiTietService.deleteByIdKhachHang(idKhachHang);
        System.out.println("HoaDonChiTietService.addHoaDonChiTietUser: " + hoaDon.getMa());
    }


    public Integer getSoPhanTramGiamByIdHoaDon(UUID id) {
        Integer soPhanTramGiam = hoaDonRepository.getSoPhanTramGiamByIdHoaDon(id);
        if (soPhanTramGiam == null) {
            return 0;
        } else {
            return soPhanTramGiam;
        }
    }

    public BigDecimal sumTongTienByIdHoaDon(UUID idHoaDon) {
        BigDecimal tongTien = sumTongTien(idHoaDon);
        Integer soPhanTramGiam = getSoPhanTramGiamByIdHoaDon(idHoaDon);
        BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
        BigDecimal soTienSauKhiGiam = tongTien.subtract(soTienDuocGiam);
        return soTienSauKhiGiam;
    }

    public List<GioHangUserResponse> getListByIdHoaDon(UUID idHoaDon) {
        return hoaDonChiTietRepository.getListByIdHoaDon(idHoaDon);
    }

    public BigDecimal sumTongTien(UUID idHoaDon) {
        return hoaDonChiTietRepository.sumTongTienByIdHoaDon(idHoaDon);
    }

    public List<HoaDonUserResponse> findListHoaDonByKhachHang(UUID idKhachHang) {
        return hoaDonChiTietRepository.findListHoaDonByKhachHang(idKhachHang);
    }

    public Page<HoaDonUserResponse> getPage(UUID idKhachHang, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return hoaDonChiTietRepository.getPage(idKhachHang, pageable);
    }

    //thống kê
    public Double TongDoanhThu() {
        return hoaDonChiTietRepository.TongDoanhThu();
    }

    public Double TongDoanhThuNgayHienTai() {
        return hoaDonChiTietRepository.TongDoanhThuNgayHienTai();
    }

    public Double TongDoanhThuThangHienTai() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTai();
    }

    public Double TongDoanhThuThangHienTaiTru1() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru1();
    }

    public Double TongDoanhThuThangHienTaiTru2() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru2();
    }

    public Double TongDoanhThuThangHienTaiTru3() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru3();
    }

    public Double TongDoanhThuThangHienTaiTru4() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru4();
    }

    public Double TongDoanhThuThangHienTaiTru5() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru5();
    }

    public Double TongDoanhThuThangHienTaiTru6() {
        return hoaDonChiTietRepository.TongDoanhThuThangHienTaiTru6();
    }

    public Double TongDoanhThuTuanHienTai() {
        return hoaDonChiTietRepository.TongDoanhThuTuanHienTai();
    }

    public Double TongDoanhThuNamHienTai() {
        return hoaDonChiTietRepository.TongDoanhThuNamHienTai();
    }

    public Double TongDoanhThu6ThangQua() {
        return hoaDonChiTietRepository.TongDoanhThu6ThangQua();
    }

    public Double TongDoanhThu1NamQua() {
        return hoaDonChiTietRepository.TongDoanhThu1NamQua();
    }

    public String NhanVienBanDcNhieuNhat() {
        return hoaDonChiTietRepository.NhanVienBanDcNhieuNhat();
    }

    public Object SanPhamBanChayNhat() {
        return hoaDonChiTietRepository.SanPhamBanChayNhat();
    }

    public List<Object> top5SanPhamBanChay() {
        return hoaDonChiTietRepository.top5SanPhamBanChay();
    }

    public List<Object> top5SanPhamBanCham() {
        return hoaDonChiTietRepository.top5SanPhamBanCham();
    }

    public String SanPhamBanChayNhatTrongNgay() {
        return hoaDonChiTietRepository.SanPhamBanChayNhatTrongNgay();
    }

    public String SanPhamBanChayNhatTrongTuan() {
        return hoaDonChiTietRepository.SanPhamBanChayNhatTrongTuan();
    }

    public String SanPhamBanChayNhatTrongThang() {
        return hoaDonChiTietRepository.SanPhamBanChayNhatTrongThang();
    }


    public String Tru0ThangTruoc() {
        return hoaDonChiTietRepository.Tru0ThangTruoc();
    }

    public String Tru1ThangTruoc() {
        return hoaDonChiTietRepository.Tru1ThangTruoc();
    }

    public String Tru2ThangTruoc() {
        return hoaDonChiTietRepository.Tru2ThangTruoc();
    }

    public String Tru3ThangTruoc() {
        return hoaDonChiTietRepository.Tru3ThangTruoc();
    }

    public String Tru4ThangTruoc() {
        return hoaDonChiTietRepository.Tru4ThangTruoc();
    }

    public String Tru5ThangTruoc() {
        return hoaDonChiTietRepository.Tru5ThangTruoc();
    }

    public String Tru6ThangTruoc() {
        return hoaDonChiTietRepository.Tru6ThangTruoc();
    }


    // tính tổng sản phẩm đã bán
    public Integer TongSanPhamDaBan() {
        return hoaDonChiTietRepository.TongSanPhamDaBan();
    }

    // tổng đơn hàng với trạng thái là 1
    public Integer TongDonHang() {
        return hoaDonChiTietRepository.TongDonHang();
    }

    // tổng khách hàng đã mua hàng
    public Integer TongKhachHang() {
        return hoaDonChiTietRepository.TongKhachHang();
    }

    //
    public Object NhanVienBanDuocNhieuSanPhamNhat() {
        return hoaDonChiTietRepository.NhanVienBanDuocNhieuSanPhamNhat();
    }
}
