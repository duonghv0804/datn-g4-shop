package com.example.websitebanquanao.infrastructures.requests;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NhanVienRequest {
    @NotBlank(message = "Họ và tên không được trống")
    private String hoVaTen;

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Họ và tên không được trống")
    private String soDienThoai;

    private String matKhau;

    @NotBlank(message = "Địa chỉ không được trống")
    private String diaChi;

    @NotBlank(message = "Xã/Phường không được trống")
    private String xaPhuong;

    @NotBlank(message = "Quận/Huyện không được trống")
    private String quanHuyen;

    @NotBlank(message = "Tỉnh/Thành phố không được trống")
    private String tinhThanhPho;

    private UUID id;
    private String ma;
    private Integer chucVu;
    private Date ngayTao;
    private Date ngaySua;
    private Integer trangThai;

    public boolean validNull() {
        return
                StringUtils.isEmpty(hoVaTen) ||
                        StringUtils.isEmpty(email) ||
                        StringUtils.isEmpty(soDienThoai) ||
                        StringUtils.isEmpty(matKhau) ||
                        StringUtils.isEmpty(diaChi) ||
                        StringUtils.isEmpty(tinhThanhPho) ||
                        StringUtils.isEmpty(xaPhuong) ||
                        StringUtils.isEmpty(quanHuyen);
    }

    public boolean validUpdate() {
        return
                StringUtils.isEmpty(hoVaTen) ||
                        StringUtils.isEmpty(email) ||
                        StringUtils.isEmpty(soDienThoai) ||
                        StringUtils.isEmpty(diaChi) ||
                        StringUtils.isEmpty(tinhThanhPho) ||
                        StringUtils.isEmpty(xaPhuong) ||
                        StringUtils.isEmpty(quanHuyen);
    }
}
