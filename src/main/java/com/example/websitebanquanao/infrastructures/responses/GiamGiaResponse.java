package com.example.websitebanquanao.infrastructures.responses;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiamGiaResponse {
    private UUID id;
    private String ma;
    private Integer soPhanTramGiam;
    private Integer soTienToiThieu;
    private Integer soLuong;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private Date ngay_tao;
    private Date ngay_sua;
    private Integer trang_thai;
}
