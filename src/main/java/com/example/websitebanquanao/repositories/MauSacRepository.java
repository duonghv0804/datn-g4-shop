package com.example.websitebanquanao.repositories;


import com.example.websitebanquanao.entities.MauSac;
import com.example.websitebanquanao.infrastructures.responses.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    @Query("select new com.example.websitebanquanao.infrastructures.responses.MauSacResponse(m.id, m.ten,m.ngay_sua,m.ngay_tao,m.trang_thai) from MauSac m where m.trang_thai = 1 ORDER BY m.ngay_tao desc ")
    public List<MauSacResponse> getAll();

    @Query("select new com.example.websitebanquanao.infrastructures.responses.MauSacResponse(m.id, m.ten,m.ngay_sua,m.ngay_tao,m.trang_thai) from MauSac m ORDER BY CASE WHEN m.trang_thai = 1 THEN 0 ELSE 1 END, m.ngay_tao desc ")
    public Page<MauSacResponse> getPage(Pageable pageable);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.MauSacResponse(m.id, m.ten,m.ngay_sua,m.ngay_tao,m.trang_thai) from MauSac m where m.id = :id")
    public MauSacResponse getByIdResponse(@Param("id") Integer id);

    @Query("SELECT DISTINCT new com.example.websitebanquanao.infrastructures.responses.MauSacResponse(m.id, m.ten,m.ngay_sua,m.ngay_tao,m.trang_thai) FROM MauSac m INNER JOIN m.sanPhamChiTiets spct INNER JOIN spct.idSanPham sp WHERE sp.id = :idSanPham AND spct.soLuong > 0 AND spct.trangThai = 1")
    public List<MauSacResponse> getListMauSacByIdSanPham(@Param("idSanPham") UUID idSanPham);

    boolean existsByTen(String ten);
}
