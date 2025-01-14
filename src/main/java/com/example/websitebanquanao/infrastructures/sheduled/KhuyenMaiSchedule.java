package com.example.websitebanquanao.infrastructures.sheduled;

import com.example.websitebanquanao.entities.KhuyenMai;
import com.example.websitebanquanao.repositories.KhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class KhuyenMaiSchedule {
    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    @Scheduled(cron = "0 0 0 * * ?") // đến 00:00 moi ngay
    public void updateKhuyenMaiStatus() {
        Date today = new Date();
        List<KhuyenMai> khuyenMais = khuyenMaiRepository.findAll();

        for (KhuyenMai khuyenMai : khuyenMais) {
            Date ngayKetThuc = khuyenMai.getNgayKetThuc();

            if (ngayKetThuc != null) {
                // Kiểm tra nếu ngày kết thúc <= ngày hiện tại thì trạng thái chuyển thành 1 (còn hạn), ngược lại là 0 (hết hạn)
                int newTrangThai = (ngayKetThuc.compareTo(today) <= 0) ? 1 : 0;

                if (newTrangThai != khuyenMai.getTrangThai()) {
                    khuyenMai.setTrangThai(newTrangThai);
                    khuyenMaiRepository.save(khuyenMai);

                    // Hiển thị thông báo trạng thái mới của khuyến mãi
                    if (newTrangThai == 1) {
                        System.out.println("Cập nhật trạng thái Khuyến Mãi thành: Còn hạn " + khuyenMai.getMa());
                    } else {
                        System.out.println("Cập nhật trạng thái Khuyến Mãi thành: Hết hạn " + khuyenMai.getMa());
                    }
                }
            }
        }
    }
}
