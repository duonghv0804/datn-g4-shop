package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.GiamGia;
import com.example.websitebanquanao.entities.KhuyenMai;
import com.example.websitebanquanao.infrastructures.requests.GiamGiaRequest;
import com.example.websitebanquanao.infrastructures.responses.GiamGiaResponse;
import com.example.websitebanquanao.infrastructures.responses.HinhThucThanhToanResponse;
import com.example.websitebanquanao.repositories.GiamGiaRepository;
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
public class GiamGiaService {
    @Autowired
    private GiamGiaRepository giamGiaRepository;

    // admin
    public Page<GiamGiaResponse> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return giamGiaRepository.getPage(pageable);
    }

    public boolean isTenValid(String ma) {
        return ma != null && !ma.trim().isEmpty();
    }

    public String maGGCount() {
        String code = "";
        List<GiamGia> list = giamGiaRepository.findAll();
        if (list.isEmpty()) {
            code = "GG0001";
        } else {
            int max = 0;
            for (GiamGia nv : list) {
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
                code = "GG000" + max;
            } else if (max < 100) {
                code = "GG00" + max;
            } else if (max < 1000) {
                code = "GG0" + max;
            } else {
                code = "GG" + max;
            }
        }
        return code;
    }

    public void add(GiamGiaRequest giamGiaRequest) {
        GiamGia giamGia = new GiamGia();
        giamGia.setMa(maGGCount());
        giamGia.setSoPhanTramGiam(giamGiaRequest.getSoPhanTramGiam());
        giamGia.setSoTienToiThieu(giamGiaRequest.getSoTienToiThieu());
        giamGia.setSoLuong(giamGiaRequest.getSoLuong());
        giamGia.setNgayBatDau(giamGiaRequest.getNgayBatDau());
        giamGia.setNgayKetThuc(giamGiaRequest.getNgayKetThuc());
        giamGia.setTrang_thai(1);
        java.util.Date currentDate = new java.util.Date();
        if (giamGiaRequest.getNgay_tao() == null) {
            giamGia.setNgay_tao(new Date(currentDate.getTime()));
        } else {
            giamGia.setNgay_tao(giamGiaRequest.getNgay_tao());
        }
        giamGia.setNgay_sua(new Date(currentDate.getTime()));
        giamGiaRepository.save(giamGia);
        System.out.println("GiamGiaService.add: " + giamGia.getMa());
    }

    public void update(GiamGiaRequest giamGiaRequest, UUID id) {
        GiamGia giamGia = giamGiaRepository.findById(id).orElse(null);

        if (giamGia != null) {
            // Kiểm tra mã đã tồn tại chưa (ngoại trừ mã hiện tại)
            GiamGiaResponse existingGiamGia = giamGiaRepository.findByMa(giamGiaRequest.getMa());

            if (existingGiamGia != null && !existingGiamGia.getId().equals(id)) {
                // Mã đã tồn tại cho một giảm giá khác
                System.out.println("GiamGiaService.update: Code already exists for another discount");
                return;
            }

            // Cập nhật thông tin giảm giá
            giamGia.setSoPhanTramGiam(giamGiaRequest.getSoPhanTramGiam());
            giamGia.setSoTienToiThieu(giamGiaRequest.getSoTienToiThieu());
            giamGia.setSoLuong(giamGiaRequest.getSoLuong());
            giamGia.setNgayBatDau(giamGiaRequest.getNgayBatDau());
            giamGia.setNgayKetThuc(giamGiaRequest.getNgayKetThuc());

            // Cập nhật ngày sửa đổi
            java.util.Date currentDate = new java.util.Date();
            giamGia.setNgay_sua(new Date(currentDate.getTime()));

            // Xác định ngày hiện tại và ngày kết thúc
            LocalDate today = LocalDate.now();
            LocalDate ngayKetThuc = giamGia.getNgayKetThuc(); // Đảm bảo đối tượng LocalDate

            // Cập nhật trạng thái dựa trên ngày hiện tại và ngày kết thúc
            if (today.isEqual(ngayKetThuc) || today.isAfter(ngayKetThuc)) {
                giamGia.setTrang_thai(0);
                System.out.println("Cập nhật trạng thái Giảm Giá thành: Còn hạn " + giamGia.getMa());
                System.out.println(giamGia.getTrang_thai());

            } else if (today.isBefore(ngayKetThuc)) {
                giamGia.setTrang_thai(1);
                System.out.println("Cập nhật trạng thái Giảm Giá thành: Hết hạn " + giamGia.getMa());
            }

            // Lưu giảm giá đã được cập nhật
            giamGiaRepository.save(giamGia);

            System.out.println("GiamGiaService.update: " + giamGia.getMa());
        } else {
            System.out.println("GiamGiaService.update: null");
        }
    }


    public void delete(UUID id) {
        GiamGia giamGia = giamGiaRepository.findById(id).orElse(null);
        if (giamGia != null) {
            giamGiaRepository.deleteById(id);

            System.out.println("GiamGiaService.delete: " + id);
        } else {
            System.out.println("GiamGiaService.delete: null");
        }
    }

    public GiamGiaResponse getById(UUID id) {
        GiamGiaResponse giamGiaResponse = giamGiaRepository.getByIdResponse(id);
        if (giamGiaResponse != null) {
            System.out.println("GiamGiaService.findById: " + giamGiaResponse.getMa());
            return giamGiaResponse;
        } else {
            System.out.println("GiamGiaService.findById: null");
            return null;
        }
    }

    public boolean isMaValid(String ma) {
        return ma != null && !ma.trim().isEmpty();
    }

    public GiamGiaResponse getByMa(String ma) {
        return giamGiaRepository.getByMa(ma);
    }

    // user
    public GiamGiaResponse findByMa(String ma) {
        GiamGiaResponse giamGiaResponse = giamGiaRepository.findByMa(ma);
        if (giamGiaResponse != null) {
            System.out.println("GiamGiaService.findByMa: " + giamGiaResponse.getMa());
            return giamGiaResponse;
        } else {
            System.out.println("GiamGiaService.findByMa: null");
            return null;
        }
    }

    public List<GiamGiaResponse> getAll() {
        return giamGiaRepository.getALL();
    }


    @Transactional
    public void updateSoLuongByMa(String ma, int soLuong) {
        giamGiaRepository.updateSoLuongByMa(ma, soLuong);
    }

    public void checkNgayKetThuc() {
        LocalDate today = LocalDate.now();
        List<GiamGia> giamGias = giamGiaRepository.findAll();

        for (GiamGia giamGia : giamGias) {
            if (giamGia.getNgayKetThuc().isBefore(today) || giamGia.getNgayKetThuc().isEqual(today)) {
                giamGiaRepository.updateTrangThaiById(giamGia.getId(), 0);
            }

        }
    }
}
