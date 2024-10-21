package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.KhachHang;
import com.example.websitebanquanao.infrastructures.requests.KhachHangRequest;
import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import com.example.websitebanquanao.repositories.KhachHangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;


    //admin
    public Page<KhachHangResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return khachHangRepository.getPage(pageable);
    }

    public List<KhachHangResponse> getAll() {
        return khachHangRepository.findAllKhachHang();
    }

    public void add(KhachHangRequest khachHangRequest) {
        KhachHang khachHang = new KhachHang();
        khachHang.setHoVaTen(khachHangRequest.getHoVaTen());
        khachHang.setEmail(khachHangRequest.getEmail());
        khachHang.setSoDienThoai(khachHangRequest.getSoDienThoai());
        khachHang.setMatKhau(khachHangRequest.getMatKhau());
        khachHang.setDiaChi(khachHangRequest.getDiaChi());
        khachHang.setXaPhuong(khachHangRequest.getXaPhuong());
        khachHang.setQuanHuyen(khachHangRequest.getQuanHuyen());
        khachHang.setTinhThanhPho(khachHangRequest.getTinhThanhPho());
        khachHang.setTrangThai(0);
        java.util.Date currentDate = new java.util.Date();
        khachHang.setNgayTao(new java.sql.Date(currentDate.getTime()));
        khachHang.setNgaySua(new java.sql.Date(currentDate.getTime()));

        khachHangRepository.save(khachHang);

        System.out.println("KhachHangService.add: " + khachHang.getHoVaTen());
    }


    public void update(KhachHangRequest khachHangRequest, UUID id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
        if (khachHang != null) {
            khachHang.setHoVaTen(khachHangRequest.getHoVaTen());
            khachHang.setEmail(khachHangRequest.getEmail());
            khachHang.setSoDienThoai(khachHangRequest.getSoDienThoai());
            khachHang.setDiaChi(khachHangRequest.getDiaChi());
            khachHang.setXaPhuong(khachHangRequest.getXaPhuong());
            khachHang.setQuanHuyen(khachHangRequest.getQuanHuyen());
            khachHang.setTinhThanhPho(khachHangRequest.getTinhThanhPho());
            khachHang.setTrangThai(khachHangRequest.getTrangThai());
            java.util.Date currentDate = new java.util.Date();
            khachHang.setNgaySua(new java.sql.Date(currentDate.getTime()));

            khachHangRepository.save(khachHang);
            System.out.println("KhachHangService.update: " + khachHang.getHoVaTen());
        } else {
            System.out.println("KhachHangService.update: null");
        }
    }

    public void delete(UUID id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
        if (khachHang != null) {
            khachHangRepository.deleteById(id);

            System.out.println("KhachHangService.delete: " + id);
        } else {
            System.out.println("KhachHangService.delete: null");
        }
    }

    public KhachHangResponse getById(UUID id) {
        KhachHangResponse khachHangResponse = khachHangRepository.getByIdResponse(id);
        if (khachHangResponse != null) {
            System.out.println("KhachHangService.findById: " + khachHangResponse.getHoVaTen());
            return khachHangResponse;
        } else {
            System.out.println("KhachHangService.findById: null");
            return null;
        }
    }
    public KhachHang getById1(UUID id) {
        KhachHang khachHang = khachHangRepository.getById(id);
        if (khachHang != null) {
            System.out.println("KhachHangService.findById: " + khachHang.getHoVaTen());
            return khachHang;
        } else {
            System.out.println("KhachHangService.findById: null");
            return null;
        }
    }


    //user
    public KhachHangResponse getByEmailAndMatKhau(String email, String matKhau) {
        System.out.println("KhachHangService.getByEmailAndMatKhau: " + email + " " + matKhau);
        return khachHangRepository.getByEmailAndMatKhau(email, matKhau);
    }

    public boolean existsByEmail(String email) {
        System.out.println("KhachHangService.existsByEmail: " + email);
        return khachHangRepository.existsByEmail(email);
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$");
    }

    public boolean isSoDienThoai(String soDienThoai) {
        return soDienThoai.matches("^0[0-9]{9,10}$");
    }

    public boolean isEmail(String email) {
        return email.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$");
    }
}
