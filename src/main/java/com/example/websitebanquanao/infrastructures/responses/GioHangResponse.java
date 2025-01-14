package com.example.websitebanquanao.infrastructures.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class GioHangResponse {
    private UUID idHoaDonChiTiet;
    private UUID idSanPhamChiTiet;
    private UUID idSanPham;
    private String maSanPham;
    private String tenSanPham;
    private String tenMau;
    private String tenSize;
    private Long soLuong;
    private BigDecimal gia;
}
