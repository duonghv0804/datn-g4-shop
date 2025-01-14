package com.example.websitebanquanao.repositories;

import com.example.websitebanquanao.entities.SanPham;
import com.example.websitebanquanao.infrastructures.responses.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {


    // admin
    @Query("select new com.example.websitebanquanao.infrastructures.responses.SanPhamResponse(sp.id,sp.idLoai.id, sp.idThuongHieu.id, sp.idSex.id, sp.ten, sp.ngay_tao, sp.anh, sp.idLoai.ten,sp.idSex.ten,sp.idThuongHieu.ten,sp.trang_thai) from SanPham sp ORDER BY sp.ngay_tao desc ")
    public List<SanPhamResponse> getAll();


    @Query("select new com.example.websitebanquanao.infrastructures.responses.KhuyenMaiChiTietResponse(s.id, s.ten, s.idLoai.ten) from SanPham s")
    public List<KhuyenMaiChiTietResponse> getAllKhuyenMai();

    @Query(value = "SELECT distinct sp.*\n" +
            "FROM san_pham sp\n" +
            "         LEFT JOIN san_pham_chi_tiet spct ON sp.id = spct.id_san_pham\n" +
            "         LEFT JOIN khuyen_mai_chi_tiet kmct ON spct.id = kmct.id_san_pham_chi_tiet\n" +
            "        left join khuyen_mai km on kmct.id_khuyen_mai = km.id\n" +
            "        left join loai lsp on sp.id_loai = lsp.id\n" +
            "WHERE\n" +
            "      not exists(select  km.*\n" +
            "                 from khuyen_mai km join khuyen_mai_chi_tiet kmct on km.id = kmct.id_khuyen_mai\n" +
            "                 where km.trang_thai = 0\n" +
            "                   and kmct.id_san_pham_chi_tiet = spct.id\n" +
            "                )", nativeQuery = true)
    List<SanPham> getAllKhuyenMai2();

    @Query("select new com.example.websitebanquanao.infrastructures.responses.SanPhamResponse(sp.id, sp.idLoai.id, sp.idThuongHieu.id, sp.idSex.id, sp.ten, sp.ngay_tao, sp.anh, sp.idLoai.ten, sp.idThuongHieu.ten, sp.idSex.ten, sp.trang_thai) from SanPham sp ORDER BY CASE WHEN sp.trang_thai = 1 THEN 0 ELSE 1 END, sp.ngay_tao desc ")
    public Page<SanPhamResponse> getPage(Pageable pageable);


    @Query("select new com.example.websitebanquanao.infrastructures.responses.SanPhamResponse(sp.id,sp.idLoai.id, sp.idThuongHieu.id, sp.idSex.id, sp.ten, sp.ngay_tao, sp.anh, sp.idLoai.ten,sp.idThuongHieu.ten,sp.idSex.ten,sp.trang_thai) from SanPham sp where sp.id = :id")
    public SanPhamResponse getByIdResponse(@Param("id") UUID id);


    // user
    @Query("select new com.example.websitebanquanao.infrastructures.responses.TrangChuResponse(s.id, s.ten, s.anh, min(spct.gia), min(ms.id), s.ngay_tao,min(kc.id)) from SanPham s join s.sanPhamChiTiets spct join spct.idMauSac ms join spct.idKichCo kc where spct.trangThai = 1 and spct.soLuong >0 group by s.id, s.ten, s.anh, s.ngay_tao order by s.ngay_tao desc")
    public List<TrangChuResponse> getListTrangChu();

    @Query(value = "SELECT TOP 5 s.id, s.ten, s.anh, MIN(spct.gia), MIN(ms.id), s.ngay_tao,MIN(kc.id) " +
            "FROM san_pham s " +
            "JOIN san_pham_chi_tiet spct ON s.id = spct.id_san_pham " +
            "JOIN kich_co kc ON spct.id_kich_co = kc.id " +
            "JOIN mau_sac ms ON spct.id_mau_sac = ms.id " +
            "JOIN hoa_don_chi_tiet hdct ON spct.id = hdct.id_san_pham_chi_tiet " +
            "JOIN hoa_don hd ON hdct.id_hoa_don = hd.id " +
            "WHERE spct.trang_thai = 1 " + "And spct.so_luong > 0 " +
            "GROUP BY s.id, s.ten, s.anh, s.ngay_tao " +
            "ORDER BY COUNT(hdct.id) DESC", nativeQuery = true)
    public List<Object[]> getTop5BestSellingProducts();


    @Query("select new com.example.websitebanquanao.infrastructures.responses.TrangChuResponse(s.id, s.ten, s.anh, MIN(spct.gia), MIN(ms.id), s.ngay_tao,Min(kc.id)) from SanPham s join s.sanPhamChiTiets spct join spct.idMauSac ms join spct.idKichCo kc join spct.khuyenMaiChiTiets kmct join kmct.idKhuyenMai km where km.ngayBatDau <= CURRENT_DATE and km.ngayKetThuc >= CURRENT_DATE and spct.trangThai = 1 and spct.soLuong > 0 group by s.id, s.ten, s.anh, s.ngay_tao order by s.ngay_tao desc")
    public List<TrangChuResponse> getListSale();

    @Query("select new com.example.websitebanquanao.infrastructures.responses.TrangChuResponse(s.id, s.ten, s.anh, min(spct.gia), min(ms.id), s.ngay_tao,Min(kc.id)) from SanPham s join s.sanPhamChiTiets spct join spct.idKichCo kc join spct.idMauSac ms where s.idLoai.id = :idLoai and spct.trangThai = 1 and spct.soLuong > 0 group by s.id, s.ten, s.anh, s.ngay_tao order by s.ngay_tao desc")
    public List<TrangChuResponse> getListSanPhamByIdLoai(@Param("idLoai") Integer idLoai);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.SanPhamChiTietUserResponse(s.id, s.ten, min(spct.gia), spct.moTa,spct.trangThai,spct.maSanPham,th.ten) from SanPham s join s.sanPhamChiTiets  spct join s.idThuongHieu th where s.id = :idSanPham group by s.id, s.ten, spct.moTa,spct.maSanPham,spct.trangThai,th.ten")
    public SanPhamChiTietUserResponse getByIdSanPham(@Param("idSanPham") UUID idSanPham);

    @Query("select new com.example.websitebanquanao.infrastructures.responses.SanPhamChiTietUserResponse(s.id, s.ten, min(spct.gia), spct.moTa,spct.trangThai,spct.maSanPham,th.ten) from SanPham s join s.sanPhamChiTiets spct join s.idThuongHieu th where s.id = :idSanPham and spct.idMauSac.id = :idMauSac and spct.idKichCo.id = :idKichCo group by s.id, s.ten, spct.moTa,spct.maSanPham,spct.trangThai,th.ten")
    public SanPhamChiTietUserResponse getByIdSanPhamAndIdMauSacAndIdKichCo(@Param("idSanPham") UUID idSanPham, @Param("idMauSac") Integer idMauSac, @Param("idKichCo") Integer idKichCo);

    @Query(value = "select top 1 spct.id_kich_co from san_pham s join san_pham_chi_tiet spct on s.id = spct.id_san_pham where spct.id_san_pham= :idSanPham  and spct.id_mau_sac= :idMauSac and spct.so_luong >0 and spct.trang_thai =1", nativeQuery = true)
    public Integer getMinIdKichCoByIdMauSacnAndIdSanPham(UUID idSanPham, Integer idMauSac);

    @Query(value = "select spct.id from san_pham s join san_pham_chi_tiet spct on s.id=spct.id_san_pham where spct.id_san_pham = :idSanPham and id_kich_co= :idKichCo and id_mau_sac= :idMauSac", nativeQuery = true)
    public UUID getIdSanPhamChiTietByIdMauSacnAndIdSanPham(UUID idSanPham, Integer idMauSac, Integer idKichCo);

    @Query("SELECT DISTINCT new com.example.websitebanquanao.infrastructures.responses.LoaiResponse(sp.idLoai.id, sp.idLoai.ten,sp.idLoai.ngay_sua,sp.idLoai.ngay_sua,sp.idLoai.trang_thai) FROM SanPham sp ORDER BY sp.idLoai.ten")
    public List<LoaiResponse> getListLoai();

    @Query("SELECT DISTINCT new com.example.websitebanquanao.infrastructures.responses.ThuongHieuResponse(sp.idLoai.id, sp.idLoai.ten,sp.idLoai.ngay_sua,sp.idLoai.ngay_sua,sp.idLoai.trang_thai) FROM SanPham sp ORDER BY sp.idLoai.ten")
    public List<LoaiResponse> getListThuongHieu();

    @Query("SELECT DISTINCT new com.example.websitebanquanao.infrastructures.responses.SexResponse(sp.idLoai.id, sp.idLoai.ten,sp.idLoai.ngay_sua,sp.idLoai.ngay_sua,sp.idLoai.trang_thai) FROM SanPham sp ORDER BY sp.idLoai.ten")
    public List<LoaiResponse> getListSex();

    @Query("select sp from SanPham sp where sp.ten = :ten")
    SanPham findByTen(String ten);
}
