package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.Loai;
import com.example.websitebanquanao.entities.MauSac;
import com.example.websitebanquanao.entities.ThuongHieu;
import com.example.websitebanquanao.infrastructures.requests.LoaiRequest;
import com.example.websitebanquanao.infrastructures.requests.ThuongHieuRequest;
import com.example.websitebanquanao.infrastructures.responses.ThuongHieuResponse;
import com.example.websitebanquanao.repositories.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ThuongHieuService {
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    // admin

    public List<ThuongHieuResponse> getAll() {
        return thuongHieuRepository.getAll();
    }

    public Page<ThuongHieuResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return thuongHieuRepository.getPage(pageable);
    }

    public void add(ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setTen(thuongHieuRequest.getTen());

        // Thiết lập ngày hiện tại
        Date currentDate = new Date();

        // Thiết lập ngày tạo là ngày hiện tại nếu chưa có, chuyển đổi sang java.sql.Date
        if (thuongHieuRequest.getNgay_tao() == null) {
            thuongHieu.setNgay_tao(new java.sql.Date(currentDate.getTime()));
        } else {
            thuongHieu.setNgay_tao(thuongHieuRequest.getNgay_tao());
        }
        // Thiết lập ngày sửa là ngày hiện tại, chuyển đổi sang java.sql.Date
        thuongHieu.setNgay_sua(new java.sql.Date(currentDate.getTime()));



        thuongHieu.setTrang_thai(thuongHieuRequest.getTrang_thai());
        // Lưu đối tượng thương hiệu vào cơ sở dữ liệu
        thuongHieuRepository.save(thuongHieu);
        System.out.println("ThuongHieuService.add: " + thuongHieu.getTen());
    }


    public void update(ThuongHieuRequest thuongHieuRequest, Integer id){
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElse(null);
        if (thuongHieu != null){
            thuongHieu.setTen(thuongHieuRequest.getTen());
            thuongHieu.setTrang_thai(thuongHieuRequest.getTrang_thai());
            // Thiết lập ngày hiện tại
            Date currentDate = new Date();
            thuongHieu.setNgay_sua(new java.sql.Date(currentDate.getTime()));

            thuongHieuRepository.save(thuongHieu);
            System.out.println("thuongHieuService.update: " + thuongHieu.getTen());
        } else {
            System.out.println("thuongHieuService.update: null");
        }
    }


    public void delete(int id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElse(null);
        if (thuongHieu != null) {
            thuongHieuRepository.deleteById(id);

            System.out.println("thuongHieuService.delete: " + id);
        } else {
            System.out.println("thuongHieuService.delete: null");
        }
    }

    public ThuongHieuResponse getById(Integer id) {
        ThuongHieuResponse thuongHieuResponse = thuongHieuRepository.getByIdResponse(id);
        if (thuongHieuResponse != null) {
            System.out.println("thuongHieuService.getById: " + thuongHieuResponse.getTen());
            return thuongHieuResponse;
        } else {
            System.out.println("thuongHieuService.getById: null");
            return null;
        }
    }

    public boolean isTenValid(String ten) {
        return ten != null && !ten.trim().isEmpty(); }

    public ThuongHieu findById(Integer id) {
        // Trả về đối tượng thương hiệu nếu tìm thấy, hoặc null nếu không tìm thấy
        return thuongHieuRepository.findById(id).orElse(null);
    }

}
