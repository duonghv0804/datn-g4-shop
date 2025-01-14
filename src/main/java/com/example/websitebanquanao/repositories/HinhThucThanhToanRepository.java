package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.HinhThucThanhToan;
import com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, Integer> {
    // admin
    @Query("select new com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse(g.id, g.ma, g.ten,g.ngayTao,g.ngaySua, g.trangThai)from HinhThucThanhToan g ORDER BY CASE WHEN g.trangThai = 1 THEN 0 ELSE 1 END, g.ma desc ")
    public Page<HinhThucThanhToanResponse> getPage(Pageable pageable);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse(g.id, g.ma, g.ten,g.ngayTao,g.ngaySua, g.trangThai)from HinhThucThanhToan g ORDER BY CASE WHEN g.trangThai = 1 THEN 0 ELSE 1 END, g.ma desc")
    public List<HinhThucThanhToanResponse> getALL();

    @Query("select new com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse(g.id, g.ma, g.ten,g.ngayTao,g.ngaySua, g.trangThai) from HinhThucThanhToan g where g.id = :id")
    public HinhThucThanhToanResponse getByIdResponse(@Param("id") Integer id);

    boolean existsByTen(String ten);

    // user
    @Query("select new com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse(g.id, g.ma, g.ten,g.ngayTao,g.ngaySua, g.trangThai) from HinhThucThanhToan g where g.ma = :ma")
    public HinhThucThanhToanResponse findByMa(@Param("ma") String ma);


    @Query("select new com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse(g.id, g.ma, g.ten,g.ngayTao,g.ngaySua, g.trangThai) from HinhThucThanhToan g where g.ma = :ma")
    public HinhThucThanhToanResponse getByMa(@Param("ma") String ma);
}
