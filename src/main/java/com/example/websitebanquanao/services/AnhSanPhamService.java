package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.AnhSanPham;
import com.example.websitebanquanao.entities.SanPham;
import com.example.websitebanquanao.entities.SanPhamChiTiet;
import com.example.websitebanquanao.infrastructures.requests.AnhSanPhamRequest;
import com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse;
import com.example.websitebanquanao.repositories.AnhSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnhSanPhamService {
    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;

    // admin
    public List<AnhSanPhamResponse> getAll(UUID id) {
        return anhSanPhamRepository.getAll(id);
    }

    public void add(SanPham sanPham, List<String> duongDan) {
        for (String string : duongDan) {
            AnhSanPham anhSanPham = new AnhSanPham();
            anhSanPham.setDuongDan(string);
            anhSanPham.setIdSanPham(sanPham);
            anhSanPham.setTrangThai(1);
            anhSanPhamRepository.save(anhSanPham);
        }
        System.out.println("AnhSanPhamService.add: " + sanPham.getId());
    }

    public void update(AnhSanPhamRequest anhSanPhamRequest, UUID id) {
        AnhSanPham anhSanPham = anhSanPhamRepository.findById(id).orElse(null);
        if (anhSanPham != null) {
            anhSanPham.setDuongDan(anhSanPhamRequest.getDuongDan());
            anhSanPhamRepository.save(anhSanPham);

            System.out.println("AnhSanPhamService.update: " + id);
        }
    }

    public void delete(UUID id) {
        AnhSanPham anhSanPham = anhSanPhamRepository.findById(id).orElse(null);
        if (anhSanPham != null) {
            anhSanPhamRepository.deleteById(id);
            System.out.println("AnhSanPhamService.delete: " + id);
        }
    }

    // user
//    public List<AnhSanPhamResponse> getListAnhByIdSanPhamAndIdMauSac(UUID idSanPham, Integer idMauSac){
//        return anhSanPhamRepository.getListAnhByIdSanPhamAndIdMauSac(idSanPham, idMauSac);
//    }

    public List<AnhSanPhamResponse> getListAnhByIdSanPham(UUID idSanPham) {
        return anhSanPhamRepository.getListAnhByIdSanPham(idSanPham);
    }

}
