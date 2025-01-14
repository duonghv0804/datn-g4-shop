package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.GioHangChiTiet;
import com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {
    @Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.idSanPhamChiTiet.id = :idSanPhamChiTiet AND ghct.idKhachHang.id = :idKhachHang")
    public GioHangChiTiet findByIdSanPhamChiTietIdAndIdGioHangId(UUID idSanPhamChiTiet, UUID idKhachHang);

    @Modifying
    @Query("DELETE FROM GioHangChiTiet ghct WHERE ghct.idSanPhamChiTiet.id = :idSanPhamChiTiet AND ghct.idKhachHang.id = :idKhachHang")
    public void deleteByIdSanPhamChiTietAndIdKhachHang(@Param("idSanPhamChiTiet") UUID idSanPhamChiTiet, @Param("idKhachHang") UUID idKhachHang);

    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse(sp.id, spct.id, spct.idSanPham.id, spct.maSanPham, sp.ten, ms.id, ms.ten, kc.ten, ghct.soLuong, spct.soLuong, ghct.gia) FROM GioHangChiTiet ghct JOIN ghct.idKhachHang kh JOIN ghct.idSanPhamChiTiet spct JOIN spct.idSanPham sp LEFT JOIN spct.idMauSac ms LEFT JOIN spct.idKichCo kc WHERE kh.id = :idKhachHang")
    public List<GioHangUserResponse> getListByIdKhachHang(@Param("idKhachHang") UUID idKhachHang);

    @Modifying
    @Query("DELETE FROM GioHangChiTiet ghct WHERE ghct.idKhachHang.id = :idKhachHang")
    public void deleteByIdKhachHang(@Param("idKhachHang") UUID idKhachHang);

    @Query("SELECT SUM(ghi.gia * ghi.soLuong) FROM GioHangChiTiet ghi WHERE ghi.idKhachHang.id = :khachHangId")
    public BigDecimal getTongTienByIdKhachHang(@Param("khachHangId") UUID khangHangId);

    @Query("SELECT SUM(ghi.soLuong) FROM GioHangChiTiet ghi WHERE ghi.idKhachHang.id = :khachHangId")
    public Integer sumSoLuongByIdKhachHang(@Param("khachHangId") UUID khangHangId);
}
