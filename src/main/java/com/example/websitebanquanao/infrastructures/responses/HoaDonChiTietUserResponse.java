package com.example.websitebanquanao.infrastructures.responses;

import com.example.websitebanquanao.entities.HinhThucThanhToan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonChiTietUserResponse {
    private String ma;
    private Integer trangThai;
    private HinhThucThanhToan hinhThucThanhToan;
    private Instant ngayTao;
    private Instant ngayThanhToan;
    private Instant ngayVanChuyen;
    private Instant ngayNhan;
    private String nguoiNhan;
    private String diaChi;
    private String xaPhuong;
    private String quanHuyen;
    private String tinhThanhPho;
    private String email;
    private String soDienThoai;
    private String maVanChuyen;
    private String tenDonViVanChuyen;
    private BigDecimal phiVanChuyen;
}
