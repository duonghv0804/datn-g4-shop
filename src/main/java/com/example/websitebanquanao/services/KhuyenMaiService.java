package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.KhuyenMai;
import com.example.websitebanquanao.entities.NhanVien;
import com.example.websitebanquanao.entities.ThuongHieu;
import com.example.websitebanquanao.infrastructures.requests.KhuyenMaiRequest;
import com.example.websitebanquanao.infrastructures.responses.GiamGiaResponse;
import com.example.websitebanquanao.infrastructures.responses.KhuyenMaiResponse;
import com.example.websitebanquanao.repositories.KhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class KhuyenMaiService {
    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    public Page<KhuyenMaiResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return khuyenMaiRepository.getPage(pageable);
    }

    public List<KhuyenMai> getAll() {
        return khuyenMaiRepository.findAll();
    }

    public void checkNgayKetThuc() {
        List<KhuyenMai> khuyenMaiList = khuyenMaiRepository.findAll();
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        for (KhuyenMai khuyenMai : khuyenMaiList) {
            if (khuyenMai.getNgayKetThuc().before(date)) {
                khuyenMaiRepository.updateTrangThaiById(khuyenMai.getId(), 1);
            }
        }
    }

    public String maKMCount() {
        String code = "";
        List<KhuyenMai> list = khuyenMaiRepository.findAll();
        if (list.isEmpty()) {
            code = "KM0001";
        } else {
            int max = 0;
            for (KhuyenMai nv : list) {
                String ma = nv.getMa();
                if (ma.length() >= 4) {
                    int so = Integer.parseInt(ma.substring(2));
                    if (so > max) {
                        max = so;
                    }
                }
            }
            max++;
            if (max < 10) {
                code = "KM000" + max;
            } else if (max < 100) {
                code = "KM00" + max;
            } else if (max < 1000) {
                code = "KM0" + max;
            } else {
                code = "KM" + max;
            }
        }
        return code;
    }

    public void add(KhuyenMaiRequest khuyenMaiRequest) {
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setMa(maKMCount());
        khuyenMai.setTen(khuyenMaiRequest.getTen());
        khuyenMai.setSoPhanTramGiam(khuyenMaiRequest.getSoPhanTramGiam());
        khuyenMai.setNgayBatDau(Date.valueOf(khuyenMaiRequest.getNgayBatDau()));
        khuyenMai.setNgayKetThuc(Date.valueOf(khuyenMaiRequest.getNgayKetThuc()));
        java.util.Date currentDate = new java.util.Date();
        khuyenMai.setNgay_tao(new Date(currentDate.getTime()));
        khuyenMai.setNgay_sua(new Date(currentDate.getTime()));
        khuyenMai.setTrangThai(0);

        khuyenMaiRepository.save(khuyenMai);

        System.out.println("KhuyenMaiService.add: " + khuyenMai.getMa());

    }

    public void update(KhuyenMaiRequest khuyenMaiRequest, UUID id) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id).orElse(null);
        if (khuyenMai != null) {
            khuyenMai.setTen(khuyenMaiRequest.getTen());
            khuyenMai.setSoPhanTramGiam(khuyenMaiRequest.getSoPhanTramGiam());
            khuyenMai.setNgayBatDau(Date.valueOf(khuyenMaiRequest.getNgayBatDau()));
            khuyenMai.setNgayKetThuc(Date.valueOf(khuyenMaiRequest.getNgayKetThuc()));
            java.util.Date currentDate = new java.util.Date();
            khuyenMai.setNgay_sua(new Date(currentDate.getTime()));
            khuyenMai.setTrangThai(0);

            khuyenMaiRepository.save(khuyenMai);

            System.out.println("KhuyenMaiService.update: " + khuyenMai.getMa());
        } else {
            System.out.println("KhuyenMaiService.update: null");
        }
    }

    @Transactional
    public void updateTrangThai(UUID id, int trangThai) {
        Date ngayKetThuc = Date.valueOf("2024-01-03");
        khuyenMaiRepository.updateTrangThaiById(id, trangThai);
        khuyenMaiRepository.updateNgayKetThucById(id, ngayKetThuc);
        khuyenMaiRepository.deleteKhuyenMaiChiTietById(id);
        System.out.println("KhuyenMaiService.updateTrangThai: " + id);
    }

    public void delete(UUID id) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id).orElse(null);
        if (khuyenMai != null) {
            khuyenMaiRepository.deleteById(id);
            System.out.println("KhuyenMaiService.delete: " + khuyenMai.getMa());
        } else {
            System.out.println("KhuyenMaiService.delete: null");
        }
    }

    public KhuyenMaiResponse getById(UUID id) {
        KhuyenMaiResponse khuyenMaiResponse = khuyenMaiRepository.getByIdResponse(id);
        if (khuyenMaiResponse != null) {
            System.out.println("KhuyenMaiService.getById: " + khuyenMaiResponse.getMa());
            return khuyenMaiResponse;
        } else {
            System.out.println("KhuyenMaiService.getById: null");
            return null;
        }
    }


    public KhuyenMai findById(UUID id) {
        // Trả về đối tượng thương hiệu nếu tìm thấy, hoặc null nếu không tìm thấy
        return khuyenMaiRepository.findById(id).orElse(null);
    }

    public boolean isTenValid(String ten) {
        return ten != null && !ten.trim().isEmpty();
    }

    public KhuyenMaiResponse getByMa(String ma) {
        return khuyenMaiRepository.getByMa(ma);
    }
}
