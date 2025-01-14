package com.example.websitebanquanao.infrastructures.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHangUserResponse {
    private UUID id;
    private UUID idSanPhamChiTiet;
    private UUID idSanPham;
    private String maSanPham;
    private String tenSanPham;
    private Integer idMauSac;
    private String tenMauSac;
    private String tenKichCo;
    private Integer soLuong;
    private Integer soLuongTonKho;
    private BigDecimal gia;
}
