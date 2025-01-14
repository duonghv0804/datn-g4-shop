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
public class HinhThucThanhToanResponse {
    private Integer id;
    private String ma;
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private Integer trangThai;
}
