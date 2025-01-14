package com.example.websitebanquanao.infrastructures.requests;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietRequest {

    private UUID id;
    private String maSanPham;
    private UUID idSanPham;
    private List<Integer>  idMauSac;
    private List<Integer>  idKichCo;

    private BigDecimal gia;
    private Integer soLuong;
    private String moTa;
    private Integer trangThai;
    @Temporal(TemporalType.DATE)
    private Date ngay_tao;

    @Temporal(TemporalType.DATE)
    private Date ngay_sua;
}
