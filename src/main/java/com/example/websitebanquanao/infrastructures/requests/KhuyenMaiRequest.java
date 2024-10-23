package com.example.websitebanquanao.infrastructures.requests;

import com.example.websitebanquanao.repositories.KhuyenMaiRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class KhuyenMaiRequest {
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @NotNull(message = "Số phần trăm giảm không được để trống")
    @Min(value = 1, message = "Số phần trăm giảm phải lớn hơn hoặc bằng 1")
    @Max(value = 100, message = "Số phần trăm giảm phải nhỏ hơn hoặc bằng 100")
    private Integer soPhanTramGiam;


    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate ngayKetThuc;

    private Integer trangThai;

    public boolean validNull() {
        return
                StringUtils.isEmpty(ten) ||
                        StringUtils.isEmpty(soPhanTramGiam.toString()) ||
                        StringUtils.isEmpty(ngayBatDau.toString()) ||
                        StringUtils.isEmpty(ngayKetThuc.toString());
    }
}
