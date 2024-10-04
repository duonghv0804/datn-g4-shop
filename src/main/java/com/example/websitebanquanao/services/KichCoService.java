package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.KichCo;
import com.example.websitebanquanao.infrastructures.requests.KichCoRequest;
import com.example.websitebanquanao.infrastructures.responses.KichCoResponse;
import com.example.websitebanquanao.repositories.KichCoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;

    public List<KichCoResponse> getAll() {
        return kichCoRepository.getAll();
    }

    public Page<KichCoResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return kichCoRepository.getPage(pageable);
    }

    public void add(KichCoRequest kichCoRequest) {
            KichCo kichCo = new KichCo();
            kichCo.setTen(kichCoRequest.getTen());
        java.util.Date currentDate = new java.util.Date();

        // Thiết lập ngày tạo là ngày hiện tại nếu chưa có, chuyển đổi sang java.sql.Date
        if (kichCoRequest.getNgay_tao() == null) {
            kichCo.setNgay_tao(new java.sql.Date(currentDate.getTime()));
        } else {
            kichCo.setNgay_tao(kichCoRequest.getNgay_tao());
        }
        // Thiết lập ngày sửa là ngày hiện tại, chuyển đổi sang java.sql.Date
        kichCo.setNgay_sua(new java.sql.Date(currentDate.getTime()));
        kichCo.setTrang_thai(1);

            kichCoRepository.save(kichCo);

            System.out.println("KichCoService.add: " + kichCo.getTen());

    }

    public void update(KichCoRequest kichCoRequest, Integer id){
        KichCo kichCo = kichCoRepository.findById(id).orElse(null);
        if (kichCo != null){
            kichCo.setTen(kichCoRequest.getTen());
            kichCo.setTrang_thai(kichCoRequest.getTrang_thai());
            // Thiết lập ngày hiện tại
            java.util.Date currentDate = new java.util.Date();
            kichCo.setNgay_sua(new java.sql.Date(currentDate.getTime()));
            kichCo.setTrang_thai(kichCoRequest.getTrang_thai());
            kichCoRepository.save(kichCo);
            System.out.println("kichCoService.update: " + kichCo.getTen());
        } else {
            System.out.println("kichCoService.update: null");
        }
    }

    public void delete(Integer id) {
        KichCo kichCo = kichCoRepository.findById(id).orElse(null);
        if (kichCo != null) {
            kichCoRepository.deleteById(id);

            System.out.println("KichCoService.delete: " + id);
        } else {
            System.out.println("KichCoService.delete: null");
        }
    }

    public KichCoResponse getById(Integer id) {
        KichCoResponse kichCoResponse = kichCoRepository.getByIdResponse(id);
        if (kichCoResponse != null) {
            System.out.println("KichCoService.getById: " + kichCoResponse.getTen());
            return kichCoResponse;
        } else {
            System.out.println("KichCoService.getById: null");
            return null;
        }
    }

    public List<KichCoResponse> getListKichCoByIdSanPhamAndMauSac(UUID idSanPham, Integer idMauSac){
        return kichCoRepository.getListKichCoByIdSanPhamAndMauSac(idSanPham, idMauSac);
    }
    public KichCo findById(Integer id) {
        // Trả về đối tượng thương hiệu nếu tìm thấy, hoặc null nếu không tìm thấy
        return kichCoRepository.findById(id).orElse(null);
    }
    public boolean isTenValid(String ten) {
        return ten != null && !ten.trim().isEmpty(); }
}
