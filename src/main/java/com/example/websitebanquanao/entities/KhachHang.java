package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nationalized
    @Column(name = "ho_va_ten", length = 100)
    private String hoVaTen;

    @Nationalized
    @Column(name = "email", length = 50)
    private String email;

    @Nationalized
    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @Nationalized
    @Column(name = "mat_khau", length = 50)
    private String matKhau;

    @Column(name = "xa_phuong")
    private String xaPhuong;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "tinh_thanh_pho")
    private String tinhThanhPho;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idKhachHang")
    private Set<GioHangChiTiet> gioHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

}