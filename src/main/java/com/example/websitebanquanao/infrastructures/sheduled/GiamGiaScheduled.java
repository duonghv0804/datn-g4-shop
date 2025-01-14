package com.example.websitebanquanao.infrastructures.sheduled;

import com.example.websitebanquanao.entities.GiamGia;
import com.example.websitebanquanao.repositories.GiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GiamGiaScheduled {
    @Autowired
    private GiamGiaRepository giamGiaRepository;

    //  30s Chạy 1 lần
    @Scheduled(cron = "0 0 0 * * ?") // Chạy hàng ngày lúc 00:00
    public void updateGiamGiaStatus() {
        LocalDate today = LocalDate.now();
        List<GiamGia> giamGias = giamGiaRepository.findAll();

        for (GiamGia giamGia : giamGias) {
            LocalDate ngayKetThuc = giamGia.getNgayKetThuc();

            if (ngayKetThuc != null) {
                if (today.isEqual(ngayKetThuc) || today.isAfter(ngayKetThuc)) {
                    // Ngày hiện tại >= ngày kết thúc
                    if (giamGia.getTrang_thai() != 1) {
                        giamGia.setTrang_thai(1);
                        giamGiaRepository.save(giamGia);
                        System.out.println("Cập nhật trạng thái Giảm Giá thành: Hết hạn " + giamGia.getMa());
                    }
                } else if (today.isBefore(ngayKetThuc)) {
                    // Ngày hiện tại < ngày kết thúc
                    if (giamGia.getTrang_thai() != 0) {
                        giamGia.setTrang_thai(0);
                        giamGiaRepository.save(giamGia);
                        System.out.println("Cập nhật trạng thái Giảm Giá thành: Còn hạn " + giamGia.getMa());
                    }
                }
            }
        }
    }
}

