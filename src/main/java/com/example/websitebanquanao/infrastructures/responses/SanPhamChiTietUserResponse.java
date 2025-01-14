package com.example.websitebanquanao.infrastructures.responses;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietUserResponse {
    private UUID id;
    private String ten;
    private BigDecimal gia;
    private String moTa;
    private Integer trangThai;
    private String maSanPham;
    private String tenThuongHieu;

}
