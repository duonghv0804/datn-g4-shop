package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.AnhSanPham;
import com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, UUID> {
    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse(a.id, a.duongDan) FROM AnhSanPham a where a.idSanPham.id=:id")
    public List<AnhSanPhamResponse> getAll(@Param("id") UUID id);
//
//    @Query("SELECT NEW com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse(a.id, a.duongDan) FROM AnhSanPham a INNER JOIN a.idSanPham sp WHERE sp.id = :idSanPham ")
//    public List<AnhSanPhamResponse> getListAnhByIdSanPhamAndIdMauSac(@Param("idSanPham") UUID idSanPham, @Param("idMauSac") Integer idMauSac);

    // find ảnh sản phẩm theo id sản phẩm chi tiết
    @Query("SELECT new com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse(a.id, a.duongDan) FROM AnhSanPham a INNER JOIN a.idSanPham sp where sp.id=:id")
    public List<AnhSanPhamResponse> getListAnhByIdSanPham(@Param("id") UUID id);
}
