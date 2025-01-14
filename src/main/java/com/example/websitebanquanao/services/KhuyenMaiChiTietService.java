package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.KhuyenMai;
import com.example.websitebanquanao.entities.KhuyenMaiChiTiet;
import com.example.websitebanquanao.entities.SanPhamChiTiet;
import com.example.websitebanquanao.repositories.KhuyenMaiChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class KhuyenMaiChiTietService {
    @Autowired
    private KhuyenMaiChiTietRepository khuyenMaiChiTietRepository;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    // admin
    public int getTrangThai(UUID idKhuyenMai, UUID idSanPham) {
        List<KhuyenMaiChiTiet> khuyenMaiChiTietList = khuyenMaiChiTietRepository.check(idKhuyenMai, idSanPham);
        if (khuyenMaiChiTietList.isEmpty()) {
            return 0;
        }
        return 1;
    }

    public void save(UUID idKhuyenMai, UUID idSanPham) {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamChiTietService.findByIdSanPham(idSanPham);

        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setId(idKhuyenMai);

        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            KhuyenMaiChiTiet khuyenMaiChiTiet = new KhuyenMaiChiTiet();
            khuyenMaiChiTiet.setIdKhuyenMai(khuyenMai);
            khuyenMaiChiTiet.setIdSanPhamChiTiet(sanPhamChiTiet);
            khuyenMaiChiTietRepository.save(khuyenMaiChiTiet);
        }
    }

    @Transactional
    public void delete(UUID idKhuyenMai, UUID idSanPham) {
        khuyenMaiChiTietRepository.deleteByIdKhuyenMaiAndIdSanPham(idKhuyenMai, idSanPham);
    }

    // user
    public Integer getSoPhanTramGiamByIdSanPham(UUID idSanPham) {
        if (khuyenMaiChiTietRepository.getSoPhanTramGiamByIdSanPham(idSanPham) == null) {
            return 0;
        }
        return khuyenMaiChiTietRepository.getSoPhanTramGiamByIdSanPham(idSanPham);
    }

    public Integer getSoPhanTramGiamByIdSanPhamChiTiet(UUID idSanPhamChiTiet) {
        if (khuyenMaiChiTietRepository.getSoPhanTramGiamByIdSanPhamChiTiet(idSanPhamChiTiet) == null) {
            return 0;
        }
        return khuyenMaiChiTietRepository.getSoPhanTramGiamByIdSanPhamChiTiet(idSanPhamChiTiet);
    }
}
