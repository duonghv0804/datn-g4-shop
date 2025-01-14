package com.example.websitebanquanao.infrastructures.responses;

import com.example.websitebanquanao.entities.KichCo;
import com.example.websitebanquanao.entities.MauSac;
import com.example.websitebanquanao.entities.SanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class BanHangTaiQuayResponse {
    private UUID id;
    private SanPham sanPham;
    private MauSac mauSac;
    private KichCo kichCo;
    private BigDecimal gia;

    private Integer soLuong;
    private String moTa;
    private Integer trangThai;
}

