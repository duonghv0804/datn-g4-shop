package com.example.websitebanquanao.services;
import com.example.websitebanquanao.entities.Sex;
import com.example.websitebanquanao.infrastructures.requests.SexRequest;
import com.example.websitebanquanao.infrastructures.responses.SexResponse;
import com.example.websitebanquanao.repositories.SexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SexService {
    @Autowired
    private SexRepository sexRepository;

    // admin

    public List<SexResponse> getAll() {
        return sexRepository.getAll();
    }

    public Page<SexResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return sexRepository.getPage(pageable);
    }

    public void add(SexRequest sexRequest) {
        Sex sex = new Sex();
        sex.setTen(sexRequest.getTen());

        Date currentDate = new Date();

        // Thiết lập ngày tạo là ngày hiện tại nếu chưa có, chuyển đổi sang java.sql.Date
        if (sexRequest.getNgay_tao() == null) {
            sex.setNgay_tao(new java.sql.Date(currentDate.getTime()));
        } else {
            sex.setNgay_tao(sexRequest.getNgay_tao());
        }

        // Thiết lập ngày sửa là ngày hiện tại, chuyển đổi sang java.sql.Date
        sex.setNgay_sua(new java.sql.Date(currentDate.getTime()));


        sex.setTrang_thai(sexRequest.getTrang_thai());

        // Lưu đối tượng thương hiệu vào cơ sở dữ liệu
        sexRepository.save(sex);
        System.out.println("ThuongHieuService.add: " + sex.getTen());
    }


    public void update(SexRequest sexRequest, Integer id){
        Sex sex = sexRepository.findById(id).orElse(null);
        if (sex != null){
            sex.setTen(sexRequest.getTen());
            sex.setTrang_thai(sexRequest.getTrang_thai());
            Date currentDate = new Date();
            sex.setNgay_sua(new java.sql.Date(currentDate.getTime()));

            sexRepository.save(sex);
            System.out.println("SexService.update: " + sex.getTen());
        } else {
            System.out.println("SexService.update: null");
        }
    }


    public void delete(int id) {
        Sex thuongHieu = sexRepository.findById(id).orElse(null);
        if (thuongHieu != null) {
            sexRepository.deleteById(id);

            System.out.println("SexService.delete: " + id);
        } else {
            System.out.println("SexService.delete: null");
        }
    }

    public SexResponse getById(Integer id) {
        SexResponse thuongHieuResponse = sexRepository.getByIdResponse(id);
        if (thuongHieuResponse != null) {
            System.out.println("Sex.getById: " + thuongHieuResponse.getTen());
            return thuongHieuResponse;
        } else {
            System.out.println("Sex.getById: null");
            return null;
        }
    }

    public boolean isTenValid(String ten) {
        return ten != null && !ten.trim().isEmpty(); }

    public Sex findById(Integer id) {
        return sexRepository.findById(id).orElse(null);
    }

}
