package com.example.websitebanquanao.infrastructures.requests;

import com.example.websitebanquanao.repositories.ThuongHieuRepository;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ThuongHieuRequest {
    @NotBlank(message = "Tên mà không được để trống")
    private String ten;
    @Temporal(TemporalType.DATE)
    private Date ngay_tao;
    @Temporal(TemporalType.DATE)
    private Date ngay_sua;
    private int trang_thai;

    public boolean isTenDuplicated(ThuongHieuRepository thuongHieuRepository) {
        return thuongHieuRepository.existsByTen(this.ten);
    }

}
