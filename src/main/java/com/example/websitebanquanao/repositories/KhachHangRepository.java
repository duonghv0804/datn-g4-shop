package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.KhachHang;
import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
    @Query("select new com.example.websitebanquanao.infrastructures.responses.KhachHangResponse(kh.id, kh.hoVaTen, kh.soDienThoai, kh.email, kh.diaChi, kh.xaPhuong, kh.quanHuyen, kh.tinhThanhPho,kh.trangThai) from KhachHang kh ORDER BY CASE WHEN kh.trangThai = 0 THEN 0 ELSE 1 END, kh.ngayTao desc ")
    public Page<KhachHangResponse> getPage(Pageable pageable);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.KhachHangResponse(kh.id, kh.hoVaTen, kh.soDienThoai, kh.email, kh.diaChi, kh.xaPhuong, kh.quanHuyen, kh.tinhThanhPho,kh.trangThai) from KhachHang kh where kh.id = :id")
    public KhachHangResponse getByIdResponse(@Param("id") UUID id);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.KhachHangResponse(kh.id, kh.hoVaTen, kh.soDienThoai, kh.email, kh.diaChi, kh.xaPhuong, kh.quanHuyen, kh.tinhThanhPho,kh.trangThai) from KhachHang kh where kh.email = :email and kh.matKhau = :matKhau")
    public KhachHangResponse getByEmailAndMatKhau(@Param("email") String email, @Param("matKhau") String matKhau);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.KhachHangResponse(kh.id, kh.hoVaTen, kh.soDienThoai, kh.email, kh.diaChi, kh.xaPhuong, kh.quanHuyen, kh.tinhThanhPho,kh.trangThai) from KhachHang kh")
    List<KhachHangResponse> findAllKhachHang();

    boolean existsByEmail(String email);
}
