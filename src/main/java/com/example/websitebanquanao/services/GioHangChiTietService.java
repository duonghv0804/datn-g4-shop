package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.GioHangChiTiet;
import com.example.websitebanquanao.entities.SanPhamChiTiet;
import com.example.websitebanquanao.infrastructures.requests.GioHangUserRequest;
import com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse;
import com.example.websitebanquanao.repositories.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private KhuyenMaiChiTietService khuyenMaiChiTietService;

    @Autowired
    private KhachHangService khachHangService;

    public List<GioHangUserResponse> getListByIdKhachHang(UUID idKhachHang) {
        return gioHangChiTietRepository.getListByIdKhachHang(idKhachHang);
    }

    public void add(UUID idSanPham, UUID idKhachHang, GioHangUserRequest gioHangUserRequest) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getByIdSanPhamAndIdMauSacAndIdKichCo(idSanPham, gioHangUserRequest.getIdMauSac(), gioHangUserRequest.getIdKichCo());

        GioHangChiTiet gioHang = gioHangChiTietRepository.findByIdSanPhamChiTietIdAndIdGioHangId(sanPhamChiTiet.getId(), idKhachHang);

        if (gioHang != null) {
            gioHang.setSoLuong(gioHang.getSoLuong() + gioHangUserRequest.getSoLuong());

            gioHangChiTietRepository.save(gioHang);

            System.out.println("GioHangChiTietService.update: " + gioHang.getId());
        } else {
            Integer soPhanTramGiamGia = khuyenMaiChiTietService.getSoPhanTramGiamByIdSanPhamChiTiet(sanPhamChiTiet.getId());
            BigDecimal giaBan = sanPhamChiTiet.getGia();
            BigDecimal giaBanSauKhuyenMai = giaBan.subtract(giaBan.multiply(new BigDecimal(soPhanTramGiamGia)).divide(new BigDecimal(100)));

            gioHang = new GioHangChiTiet();

            gioHang.setIdKhachHang(khachHangService.getById1(idKhachHang));
            java.util.Date currentDate = new java.util.Date();
            gioHang.setNgay_tao(new java.sql.Date(currentDate.getTime()));
            gioHang.setNgay_sua(new java.sql.Date(currentDate.getTime()));
            gioHang.setIdSanPhamChiTiet(sanPhamChiTiet);
            gioHang.setSoLuong(gioHangUserRequest.getSoLuong());
            gioHang.setGia(giaBanSauKhuyenMai);

            gioHangChiTietRepository.save(gioHang);

            System.out.println("GioHangChiTietService.add: " + gioHang.getId());
        }
    }

    @Transactional
    public void updateByIdSanPhamChiTietAndIdKhachHang(UUID idSanPhamChiTiet, UUID idKhachHang, Integer soLuong) {

        GioHangChiTiet gioHang = gioHangChiTietRepository.findByIdSanPhamChiTietIdAndIdGioHangId(idSanPhamChiTiet, idKhachHang);
        java.util.Date currentDate = new java.util.Date();
        gioHang.setNgay_sua(new java.sql.Date(currentDate.getTime()));
        gioHang.setSoLuong(soLuong);

        gioHangChiTietRepository.save(gioHang);

        System.out.println("GioHangChiTietService.updateByIdSanPhamChiTietAndIdKhachHang: " + gioHang.getId());
    }

    @Transactional
    public void deleteByIdSanPhamChiTietAndIdKhachHang(UUID idSanPhamChiTiet, UUID idKhachHang) {
        gioHangChiTietRepository.deleteByIdSanPhamChiTietAndIdKhachHang(idSanPhamChiTiet, idKhachHang);
        System.out.println("GioHangChiTietService.deleteByIdSanPhamChiTietAndIdKhachHang: " + idSanPhamChiTiet + " " + idKhachHang);
    }

    @Transactional
    public void deleteByIdKhachHang(UUID idKhachHang) {
        gioHangChiTietRepository.deleteByIdKhachHang(idKhachHang);
        System.out.println("GioHangChiTietService.deleteByIdKhachHang: " + idKhachHang);
    }

    public BigDecimal getTongTienByIdKhachHang(UUID idKhachHang) {
        if (gioHangChiTietRepository.getTongTienByIdKhachHang(idKhachHang) == null) {
            return new BigDecimal(0);
        } else {
            return gioHangChiTietRepository.getTongTienByIdKhachHang(idKhachHang);
        }
    }

    public Integer sumSoLuongByIdKhachHang(UUID idKhachHang) {
        Integer sumSoLuong = gioHangChiTietRepository.sumSoLuongByIdKhachHang(idKhachHang);
        if (sumSoLuong == null) {
            return 0;
        } else {
            return sumSoLuong;
        }
    }
}
