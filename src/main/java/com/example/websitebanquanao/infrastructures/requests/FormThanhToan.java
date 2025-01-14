package com.example.websitebanquanao.infrastructures.requests;

import com.example.websitebanquanao.entities.HinhThucThanhToan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormThanhToan {
    private String hoTen;
    private String tinhThanhPho;
    private String quanHuyen;
    private String xaPhuong;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private HinhThucThanhToan hinhThucThanhToan;
    private String ghiChu;
}
