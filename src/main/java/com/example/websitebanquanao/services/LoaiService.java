package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.Loai;
import com.example.websitebanquanao.entities.ThuongHieu;
import com.example.websitebanquanao.infrastructures.requests.LoaiRequest;
import com.example.websitebanquanao.infrastructures.requests.LoaiRequest;
import com.example.websitebanquanao.infrastructures.responses.LoaiResponse;
import com.example.websitebanquanao.infrastructures.responses.LoaiResponse;
import com.example.websitebanquanao.repositories.LoaiRepository;
import com.example.websitebanquanao.repositories.LoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LoaiService {
    @Autowired
    private LoaiRepository loaiRepository;

    // admin

    public List<LoaiResponse> getAll() {
        return loaiRepository.getAll();
    }

    public Page<LoaiResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return loaiRepository.getPage(pageable);
    }

    public void add(LoaiRequest loaiRequest) {
        Loai loai = new Loai();
        loai.setTen(loaiRequest.getTen());

        // Thiết lập ngày hiện tại
        Date currentDate = new Date();

        // Thiết lập ngày tạo là ngày hiện tại nếu chưa có, chuyển đổi sang java.sql.Date
        if (loaiRequest.getNgay_tao() == null) {
            loai.setNgay_tao(new java.sql.Date(currentDate.getTime()));
        } else {
            loai.setNgay_tao(loaiRequest.getNgay_tao());
        }

        // Thiết lập ngày sửa là ngày hiện tại, chuyển đổi sang java.sql.Date
        loai.setNgay_sua(new java.sql.Date(currentDate.getTime()));



        loai.setTrang_thai(loaiRequest.getTrang_thai());

        // Lưu đối tượng thương hiệu vào cơ sở dữ liệu
        loaiRepository.save(loai);
        System.out.println("ThuongHieuService.add: " + loai.getTen());
    }


    public void update(LoaiRequest loaiRequest, Integer id){
        Loai loai = loaiRepository.findById(id).orElse(null);
        if (loai != null){
            loai.setTen(loaiRequest.getTen());
            loai.setTrang_thai(loaiRequest.getTrang_thai());

            Date currentDate = new Date();
            loai.setNgay_sua(new java.sql.Date(currentDate.getTime()));

            loaiRepository.save(loai);
            System.out.println("thuongHieuService.update: " + loai.getTen());
        } else {
            System.out.println("thuongHieuService.update: null");
        }
    }


    public void delete(int id) {
        Loai loai = loaiRepository.findById(id).orElse(null);
        if (loai != null) {
            loaiRepository.deleteById(id);

            System.out.println("thuongHieuService.delete: " + id);
        } else {
            System.out.println("thuongHieuService.delete: null");
        }
    }

    public LoaiResponse getById(Integer id) {
        LoaiResponse thuongHieuResponse = loaiRepository.getByIdResponse(id);
        if (thuongHieuResponse != null) {
            System.out.println("loaiService.getById: " + thuongHieuResponse.getTen());
            return thuongHieuResponse;
        } else {
            System.out.println("loaiService.getById: null");
            return null;
        }
    }

    public boolean isTenValid(String ten) {
        return ten != null && !ten.trim().isEmpty(); }

    public Loai findById(Integer id) {
        // Trả về đối tượng thương hiệu nếu tìm thấy, hoặc null nếu không tìm thấy
        return loaiRepository.findById(id).orElse(null);
    }
}
