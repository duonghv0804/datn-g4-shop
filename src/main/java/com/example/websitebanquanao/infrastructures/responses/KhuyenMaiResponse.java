package com.example.websitebanquanao.infrastructures.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhuyenMaiResponse {
    private UUID id;
    private String ma;
    private String ten;
    private Integer soPhanTramGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Integer trangThai;
}
