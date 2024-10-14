package com.example.websitebanquanao.infrastructures.responses;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class SanPhamResponse {
    private UUID id;
    private Integer idLoai;
    private Integer idThuongHieu;
    private Integer idSex;
    private String ten;
    private Date ngayTao;
    private String anh;
    private String tenLoai;
    private String tenThuongHieu;
    private String tenSex;
    private Integer trang_thai;


}
