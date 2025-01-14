package com.example.websitebanquanao.infrastructures.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonUserResponse {
    private UUID id;
    private String ma;
    private Instant ngayTao;
    private Integer trangThai;
    private BigDecimal tongTien;
    private Instant ngayThanhToan;
}
