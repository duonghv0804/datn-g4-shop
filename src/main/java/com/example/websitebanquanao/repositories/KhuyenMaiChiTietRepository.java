package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.KhuyenMaiChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KhuyenMaiChiTietRepository extends JpaRepository<KhuyenMaiChiTiet, UUID> {
    // admin
    @Query("SELECT kmct FROM KhuyenMaiChiTiet kmct WHERE kmct.idKhuyenMai.id = :idKhuyenMai AND kmct.idSanPhamChiTiet.idSanPham.id = :idSanPham")
    public List<KhuyenMaiChiTiet> check(@Param("idKhuyenMai") UUID idKhuyenMai, @Param("idSanPham") UUID idSanPham);

    @Modifying
    @Query("DELETE FROM KhuyenMaiChiTiet kmct WHERE kmct.idKhuyenMai.id = :idKhuyenMai AND kmct.idSanPhamChiTiet.idSanPham.id = :idSanPham")
    public void deleteByIdKhuyenMaiAndIdSanPham(@Param("idKhuyenMai") UUID idKhuyenMai, @Param("idSanPham") UUID idSanPham);

    // user
    @Query("select kmct.idKhuyenMai.soPhanTramGiam from KhuyenMaiChiTiet kmct where kmct.idSanPhamChiTiet.idSanPham.id = :idSanPham and kmct.idKhuyenMai.trangThai = 0 and kmct.idKhuyenMai.ngayBatDau <= CURRENT_DATE and kmct.idKhuyenMai.ngayKetThuc >= CURRENT_DATE")
    public Integer getSoPhanTramGiamByIdSanPham(@Param("idSanPham") UUID idSanPham);


    @Query("select kmct.idKhuyenMai.soPhanTramGiam from KhuyenMaiChiTiet kmct where kmct.idSanPhamChiTiet.id = :idSanPhamChiTiet and kmct.idKhuyenMai.trangThai = 0 and kmct.idKhuyenMai.ngayBatDau <= CURRENT_DATE and kmct.idKhuyenMai.ngayKetThuc >= CURRENT_DATE")
    public Integer getSoPhanTramGiamByIdSanPhamChiTiet(@Param("idSanPhamChiTiet") UUID idSanPhamChiTiet);
}
