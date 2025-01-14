package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.HinhThucThanhToan;
import com.example.websitebanquanao.infrastructures.requests.HinhThucThanhToanRequest;
import com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse;
import com.example.websitebanquanao.repositories.HinhThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class HinhThucThanhToanService {
    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    public List<HinhThucThanhToanResponse> getAll() {
        return hinhThucThanhToanRepository.getALL();
    }

    // admin
    public Page<HinhThucThanhToanResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return hinhThucThanhToanRepository.getPage(pageable);
    }

    public boolean isTenValid(String ma) {
        return ma != null && !ma.trim().isEmpty();
    }

    public HinhThucThanhToan findById(Integer id) {
        return hinhThucThanhToanRepository.findById(id).orElse(null);
    }

    public String maKMCount() {
        String code = "";
        List<HinhThucThanhToan> list = hinhThucThanhToanRepository.findAll();
        if (list.isEmpty()) {
            code = "1";
        } else {
            int max = 0;
            for (HinhThucThanhToan nv : list) {
                String ma = nv.getMa();
                if (ma.length() >= 0) {
                    int so = Integer.parseInt(ma);
                    if (so > max) {
                        max = so;
                    }
                }
            }
            max++;
            code = String.valueOf(max);
        }
        return code;
    }

    public void add(HinhThucThanhToanRequest hinhThucThanhToanRequest) {
        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setMa(maKMCount());
        hinhThucThanhToan.setTen(hinhThucThanhToanRequest.getTen());
        hinhThucThanhToan.setTrangThai(hinhThucThanhToanRequest.getTrangThai());
        java.util.Date currentDate = new java.util.Date();
        hinhThucThanhToan.setNgayTao(new Date(currentDate.getTime()));
        hinhThucThanhToan.setNgaySua(new Date(currentDate.getTime()));
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        System.out.println("HinhThucThanhToanService.add: " + hinhThucThanhToanRequest.getMa());
    }

    public void update(HinhThucThanhToanRequest hinhThucThanhToanRequest, Integer id) {
        HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanRepository.findById(id).orElse(null);

        if (hinhThucThanhToan != null) {
            // Kiểm tra mã đã tồn tại chưa (ngoại trừ mã hiện tại)
            HinhThucThanhToanResponse existingHinhThucThanhToan = hinhThucThanhToanRepository.findByMa(hinhThucThanhToanRequest.getMa());

            if (existingHinhThucThanhToan != null && !existingHinhThucThanhToan.getId().equals(id)) {
                System.out.println("HinhThucThanhToanService.update: Code already exists for another discount");
                return;
            }

            // Cập nhật thông tin giảm giá
            hinhThucThanhToan.setTen(hinhThucThanhToanRequest.getTen());
            hinhThucThanhToan.setTrangThai(hinhThucThanhToanRequest.getTrangThai());

            // Cập nhật ngày sửa đổi
            java.util.Date currentDate = new java.util.Date();
            hinhThucThanhToan.setNgaySua(new Date(currentDate.getTime()));

            // Lưu giảm giá đã được cập nhật
            hinhThucThanhToanRepository.save(hinhThucThanhToan);

            System.out.println("HinhThucThanhToanService.update: " + hinhThucThanhToan.getMa());
        } else {
            System.out.println("HinhThucThanhToanService.update: null");
        }
    }


    public void delete(Integer id) {
        HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanRepository.findById(id).orElse(null);
        if (hinhThucThanhToan != null) {
            hinhThucThanhToanRepository.deleteById(id);

            System.out.println("HinhThucThanhToanService.delete: " + id);
        } else {
            System.out.println("HinhThucThanhToanService.delete: null");
        }
    }

    public HinhThucThanhToanResponse getById(Integer id) {
        HinhThucThanhToanResponse hinhThucThanhToanResponse = hinhThucThanhToanRepository.getByIdResponse(id);
        if (hinhThucThanhToanResponse != null) {
            System.out.println("HinhThucThanhToanService.findById: " + hinhThucThanhToanResponse.getMa());
            return hinhThucThanhToanResponse;
        } else {
            System.out.println("HinhThucThanhToanService.findById: null");
            return null;
        }
    }

    public boolean isMaValid(String ma) {
        return ma != null && !ma.trim().isEmpty();
    }

    public HinhThucThanhToanResponse getByMa(String ma) {
        return hinhThucThanhToanRepository.getByMa(ma);
    }

    // user
    public HinhThucThanhToanResponse findByMa(String ma) {
        HinhThucThanhToanResponse hinhThucThanhToanResponse = hinhThucThanhToanRepository.findByMa(ma);
        if (hinhThucThanhToanResponse != null) {
            System.out.println("HinhThucThanhToanService.findByMa: " + hinhThucThanhToanResponse.getMa());
            return hinhThucThanhToanResponse;
        } else {
            System.out.println("HinhThucThanhToanService.findByMa: null");
            return null;
        }
    }

//    @Transactional
//    public void updateSoLuongByMa(String ma, int soLuong) {
//        hinhThucThanhToanRepository.updateSoLuongByMa(ma);
//    }
}
