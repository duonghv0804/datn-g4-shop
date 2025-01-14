package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.HoaDon;
import com.example.websitebanquanao.entities.HoaDonChiTiet;
import com.example.websitebanquanao.infrastructures.responses.GioHangResponse;
import com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse;
import com.example.websitebanquanao.infrastructures.responses.HoaDonUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    // admin
    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.GioHangResponse(MIN(hdct.id), hdct.idSanPhamChiTiet.id, hdct.idSanPhamChiTiet.idSanPham.id, hdct.idSanPhamChiTiet.maSanPham, hdct.idSanPhamChiTiet.idSanPham.ten, MIN(hdct.idSanPhamChiTiet.idMauSac.ten), MIN(hdct.idSanPhamChiTiet.idKichCo.ten), SUM(hdct.soLuong), min(hdct.gia)) FROM HoaDonChiTiet hdct WHERE hdct.idHoaDon.id = :hoaDonId GROUP BY hdct.idSanPhamChiTiet.id,hdct.idSanPhamChiTiet.idSanPham.id, hdct.idSanPhamChiTiet.maSanPham, hdct.idSanPhamChiTiet.idSanPham.ten, hdct.idSanPhamChiTiet.gia")
    List<GioHangResponse> findTotalQuantityByHoaDonId(@Param("hoaDonId") UUID hoaDonId);

    @Query("SELECT hdct FROM HoaDonChiTiet hdct WHERE hdct.idHoaDon.id = :idHoaDon AND hdct.idSanPhamChiTiet.id = :idSanPhamChiTiet")
    public HoaDonChiTiet findByHoaDonIdAndSanPhamChiTietId(UUID idHoaDon, UUID idSanPhamChiTiet);

    // user
    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse(sp.id, spct.id, spct.idSanPham.id, spct.maSanPham, sp.ten, ms.id, ms.ten, kc.ten, hdct.soLuong, spct.soLuong, hdct.gia) FROM HoaDonChiTiet hdct JOIN hdct.idSanPhamChiTiet spct JOIN spct.idSanPham sp LEFT JOIN spct.idMauSac ms LEFT JOIN spct.idKichCo kc WHERE hdct.idHoaDon.id = :idHoaDon")
    public List<GioHangUserResponse> getListByIdHoaDon(@Param("idHoaDon") UUID idHoaDon);

    @Query("SELECT SUM(hdct.gia * hdct.soLuong) FROM HoaDonChiTiet hdct WHERE hdct.idHoaDon.id = :idHoaDon")
    public BigDecimal sumTongTienByIdHoaDon(@Param("idHoaDon") UUID idHoaDon);

    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.HoaDonUserResponse(hd.id, hd.ma, hd.ngayTao, hd.trangThai, SUM(hdct.gia * hdct.soLuong),hd.ngayThanhToan) FROM HoaDonChiTiet hdct JOIN hdct.idHoaDon hd WHERE hd.idKhachHang.id = :idKhachHang GROUP BY hd.id, hd.ma, hd.ngayTao, hd.trangThai,hd.ngayThanhToan order by hd.ngayTao desc")
    public List<HoaDonUserResponse> findListHoaDonByKhachHang(@Param("idKhachHang") UUID idKhachHang);

    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.HoaDonUserResponse(hd.id, hd.ma, hd.ngayTao, hd.trangThai, SUM(hdct.gia * hdct.soLuong),hd.ngayThanhToan) FROM HoaDonChiTiet hdct JOIN hdct.idHoaDon hd WHERE hd.idKhachHang.id = :idKhachHang GROUP BY hd.id, hd.ma, hd.ngayTao, hd.trangThai,hd.ngayThanhToan order by hd.ngayTao desc")
    public Page<HoaDonUserResponse> getPage(@Param("idKhachHang") UUID idKhachHang,Pageable pageable);

    // thống kê
    @Query("SELECT SUM(hdct.gia * hdct.soLuong) FROM HoaDonChiTiet hdct " +
            "WHERE hdct.idHoaDon.trangThai = 1")
    Double TongDoanhThu();

    @Query("SELECT SUM(hdct.gia * hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.idHoaDon.trangThai = 1 " +
            "AND CAST(hdct.idHoaDon.ngayThanhToan AS date) = CAST(CURRENT_TIMESTAMP AS date)")
    Double TongDoanhThuNgayHienTai();

    @Query("SELECT SUM(hdct.gia * hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "JOIN hdct.idHoaDon hoaDon " +
            "WHERE hoaDon.trangThai = 1 " +
            "AND FUNCTION('YEAR', hoaDon.ngayThanhToan) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND FUNCTION('MONTH', hoaDon.ngayThanhToan) = FUNCTION('MONTH', CURRENT_DATE)")
    Double TongDoanhThuThangHienTai();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND DATEPART(WEEK, hoaDon.ngay_thanh_toan) = DATEPART(WEEK, GETDATE()) " +
            "AND DATEPART(YEAR, hoaDon.ngay_thanh_toan) = DATEPART(YEAR, GETDATE())",
            nativeQuery = true)
    Double TongDoanhThuTuanHienTai();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 1 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru1();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 2 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru2();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 3 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru3();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 4 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru4();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 5 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru5();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND MONTH(hoaDon.ngay_thanh_toan) = MONTH(GETDATE()) - 6 " +
            "AND YEAR(hoaDon.ngay_thanh_toan) = YEAR(GETDATE())", nativeQuery = true)
    Double TongDoanhThuThangHienTaiTru6();

    @Query("SELECT SUM(hdct.gia * hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.idHoaDon.trangThai = 1 " +
            "AND YEAR(hdct.idHoaDon.ngayThanhToan) = YEAR(CURRENT_DATE)")
    Double TongDoanhThuNamHienTai();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND hoaDon.ngay_thanh_toan BETWEEN DATEADD(MONTH, -6, GETDATE()) AND GETDATE()",
            nativeQuery = true)
    Double TongDoanhThu6ThangQua();

    @Query(value = "SELECT SUM(hdct.gia * hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hoaDon ON hdct.id_hoa_don = hoaDon.id " +
            "WHERE hoaDon.trang_thai = 1 " +
            "AND hoaDon.ngay_thanh_toan BETWEEN DATEADD(MONTH, -12, GETDATE()) AND GETDATE()",
            nativeQuery = true)
    Double TongDoanhThu1NamQua();

    @Query(value = "SELECT TOP 1 nv.ho_va_ten " +
            "FROM nhan_vien nv " +
            "JOIN hoa_don hd ON nv.id = hd.id_nhan_vien " +
            "JOIN hoa_don_chi_tiet hdt ON hd.id = hdt.id_hoa_don " +
            "GROUP BY nv.ho_va_ten " +
            "ORDER BY SUM(hdt.so_luong) DESC", nativeQuery = true)
    String NhanVienBanDcNhieuNhat();




    @Query(value = "SELECT TOP 1 sp.ten " +
            "FROM san_pham sp " +
            "JOIN san_pham_chi_tiet spt ON sp.id = spt.id_san_pham " +
            "JOIN hoa_don_chi_tiet hdct ON spt.id = hdct.id_san_pham_chi_tiet " +
            "JOIN hoa_don hd ON hdct.id_hoa_don = hd.id " +
            "WHERE hd.trang_thai = 1 " +
            "AND CAST(hd.ngay_thanh_toan AS date) = CAST(GETDATE() AS date) " +
            "GROUP BY sp.ten " +
            "ORDER BY SUM(hdct.gia * hdct.so_luong) DESC ", nativeQuery = true)
    String SanPhamBanChayNhatTrongNgay();

    @Query(value = "WITH TongGiaTriTheoSanPham AS ( " +
            "SELECT " +
            "    sp.id AS id_san_pham, " +
            "    sp.ten AS ten_san_pham, " +
            "    SUM(hdct.gia * hdct.so_luong) AS tong_gia_tri " +
            "FROM " +
            "    hoa_don_chi_tiet hdct " +
            "    INNER JOIN san_pham_chi_tiet spct ON hdct.id_san_pham_chi_tiet = spct.id " +
            "    INNER JOIN san_pham sp ON spct.id_san_pham = sp.id " +
            "    INNER JOIN hoa_don hd ON hdct.id_hoa_don = hd.id " +
            "WHERE " +
            "    hd.trang_thai = 1 " +
            "    AND DATEPART(WEEK, hd.ngay_thanh_toan) = DATEPART(WEEK, GETDATE()) " +
            "    AND YEAR(hd.ngay_thanh_toan) = YEAR(GETDATE()) " +
            "GROUP BY " +
            "    sp.id, sp.ten " +
            ") " +
            "SELECT TOP 1 ten_san_pham " +
            "FROM TongGiaTriTheoSanPham " +
            "ORDER BY tong_gia_tri DESC", nativeQuery = true)
    String SanPhamBanChayNhatTrongTuan();

    @Query(value = "WITH TongGiaTriTheoSanPham AS ( " +
            "    SELECT " +
            "        sp.id AS id_san_pham, " +
            "        sp.ten AS ten_san_pham, " +
            "        SUM(hdct.gia * hdct.so_luong) AS tong_gia_tri " +
            "    FROM " +
            "        hoa_don_chi_tiet hdct " +
            "        INNER JOIN san_pham_chi_tiet spct ON hdct.id_san_pham_chi_tiet = spct.id " +
            "        INNER JOIN san_pham sp ON spct.id_san_pham = sp.id " +
            "        INNER JOIN hoa_don hd ON hdct.id_hoa_don = hd.id " +
            "    WHERE " +
            "        hd.trang_thai = 1 " +
            "        AND MONTH(hd.ngay_thanh_toan) = MONTH(GETDATE()) " +
            "        AND YEAR(hd.ngay_thanh_toan) = YEAR(GETDATE()) " +
            "    GROUP BY " +
            "        sp.id, sp.ten " +
            ") " +
            "SELECT TOP 1 ten_san_pham " +
            "FROM TongGiaTriTheoSanPham " +
            "ORDER BY tong_gia_tri DESC", nativeQuery = true)
    String SanPhamBanChayNhatTrongThang();


    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -0, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru0ThangTruoc();
    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -1, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru1ThangTruoc();

    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -2, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru2ThangTruoc();

    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -3, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru3ThangTruoc();

    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -4, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru4ThangTruoc();

    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -5, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru5ThangTruoc();

    @Query(value = "SELECT FORMAT(DATEADD(MONTH, -6, GETDATE()), 'MM-yyyy')", nativeQuery = true)
    String Tru6ThangTruoc();

    @Query(value = "SELECT SUM(hdct.so_luong) " +
            "FROM hoa_don_chi_tiet hdct " +
            "JOIN hoa_don hd ON hdct.id_hoa_don = hd.id " +
            "WHERE hd.trang_thai = 1", nativeQuery = true)
   public Integer TongSanPhamDaBan();

    @Query(value = "SELECT COUNT(hd.id) " +
            "FROM hoa_don hd " +
            "WHERE hd.trang_thai = 1", nativeQuery = true)
   public Integer TongDonHang();
    // tổng khách hàng đã mua hàng
    @Query(value = "SELECT COUNT(DISTINCT hd.id_khach_hang) " +
            "FROM hoa_don hd " +
            "WHERE hd.trang_thai = 1", nativeQuery = true)
   public Integer TongKhachHang();

    // tìm nhân viên bán được doanh thu cao nhất và lấy ra tên nhân viên đó,số lượng sản phẩm bán được và doanh thu
    @Query(value = "SELECT TOP 1 nv.ho_va_ten AS ten_nhan_vien, COUNT(DISTINCT hd.ma) AS so_luong_san_pham_ban_duoc, SUM(hdct.gia * hdct.so_luong) AS doanh_thu " +
            "FROM hoa_don hd " +
            "JOIN hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don " +
            "JOIN nhan_vien nv ON hd.id_nhan_vien = nv.id " +
            "WHERE hd.trang_thai = 1 " +
            "GROUP BY nv.ho_va_ten " +
            "ORDER BY doanh_thu DESC", nativeQuery = true)
    public Object NhanVienBanDuocNhieuSanPhamNhat();
    //Thống kê sản phẩm bán chạy theo doanh thu, nếu muốn thống kê bán chạy theo số lượng thì bỏ nhân với giá tiền trong câu query
    @Query(value = "SELECT TOP 1 sp.ten AS ten_san_pham, hdct.so_luong AS so_luong_da_ban\n" +
            "FROM hoa_don_chi_tiet hdct join san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id\n" +
            "join hoa_don hd on hd.id = hdct.id_hoa_don\n" +
            "join san_pham sp on sp.id = spct.id_san_pham\n" +
            "WHERE hd.trang_thai = 1\n" +
            "GROUP BY sp.ten, hdct.so_luong\n" +
            "ORDER BY so_luong_da_ban DESC", nativeQuery = true)
    public Object SanPhamBanChayNhat();

    @Query(value = "SELECT TOP 5 sp.ten AS ten_san_pham, hdct.so_luong AS so_luong_da_ban, sp.anh " +
            "FROM hoa_don_chi_tiet hdct join san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id " +
            "join hoa_don hd on hd.id = hdct.id_hoa_don " +
            "join san_pham sp on sp.id = spct.id_san_pham " +
            "WHERE hd.trang_thai = 1 " +
            "GROUP BY sp.ten, hdct.so_luong, sp.anh " +
            "ORDER BY so_luong_da_ban DESC", nativeQuery = true)
    List<Object> top5SanPhamBanChay();
    @Query(value = "SELECT TOP 5 sp.ten AS ten_san_pham, hdct.so_luong AS so_luong_da_ban, sp.anh " +
            "FROM hoa_don_chi_tiet hdct join san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id " +
            "join hoa_don hd on hd.id = hdct.id_hoa_don " +
            "join san_pham sp on sp.id = spct.id_san_pham " +
            "WHERE hd.trang_thai = 1 " +
            "GROUP BY sp.ten, hdct.so_luong, sp.anh " +
            "ORDER BY so_luong_da_ban asc", nativeQuery = true)
    List<Object> top5SanPhamBanCham();

}


