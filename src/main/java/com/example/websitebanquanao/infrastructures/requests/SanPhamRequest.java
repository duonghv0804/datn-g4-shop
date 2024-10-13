package com.example.websitebanquanao.infrastructures.requests;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SanPhamRequest {
    private String ten;
    private MultipartFile anh;
    private Integer idLoai;
    private Integer idThuongHieu;
    private Integer idSex;
    @Temporal(TemporalType.DATE)
    private Date ngay_tao;
    @Temporal(TemporalType.DATE)
    private Date ngay_sua;
    private Integer trang_thai;
    private List<String> duongDan;

}
