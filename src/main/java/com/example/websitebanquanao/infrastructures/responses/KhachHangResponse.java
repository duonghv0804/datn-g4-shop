package com.example.websitebanquanao.infrastructures.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangResponse {
    private UUID id;
    private String hoVaTen;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String xaPhuong;
    private String quanHuyen;
    private String tinhThanhPho;
    private Integer trangThai;
}
