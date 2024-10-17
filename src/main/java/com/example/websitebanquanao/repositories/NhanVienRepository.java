package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.NhanVien;
import com.example.websitebanquanao.infrastructures.responses.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {
    @Query("select new com.example.websitebanquanao.infrastructures.responses.NhanVienResponse(nv.id, nv.ma, nv.hoVaTen, nv.email, nv.soDienThoai, nv.diaChi, nv.xaPhuong, nv.quanHuyen, nv.tinhThanhPho, nv.ngayVaoLam, nv.chucVu,nv.ngayTao,nv.ngaySua, nv.trangThai) from NhanVien nv ORDER BY CASE WHEN nv.trangThai = 0 THEN 0 ELSE 1 END,nv.ma desc ")
    public List<NhanVienResponse> getAll();

    @Query("select new com.example.websitebanquanao.infrastructures.responses.NhanVienResponse(nv.id, nv.ma, nv.hoVaTen, nv.email, nv.soDienThoai, nv.diaChi, nv.xaPhuong, nv.quanHuyen, nv.tinhThanhPho, nv.ngayVaoLam, nv.chucVu,nv.ngayTao,nv.ngaySua, nv.trangThai) from NhanVien nv ORDER BY CASE WHEN nv.trangThai = 0 THEN 0 ELSE 1 END,nv.ma desc ")
    public Page<NhanVienResponse> getPage(Pageable pageable);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.NhanVienResponse(nv.id, nv.ma, nv.hoVaTen, nv.email, nv.soDienThoai, nv.diaChi, nv.xaPhuong, nv.quanHuyen, nv.tinhThanhPho, nv.ngayVaoLam, nv.chucVu,nv.ngayTao,nv.ngaySua, nv.trangThai) from NhanVien nv where nv.id = :id")
    public NhanVienResponse getByIdResponse(@Param("id") UUID id);

    boolean existsByEmail(String email);

    //    check login
    @Query("select nv from NhanVien nv where nv.email = :email and nv.matKhau = :matKhau")
    public NhanVien checkLogin(@Param("email") String email, @Param("matKhau") String matKhau);

}
