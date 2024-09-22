package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "giam_gia")
public class GiamGia {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "so_phan_tram_giam")
    private Integer soPhanTramGiam;

    @Column(name = "so_phan_tien_giam_toi_thieu")
    private Integer soTienToiThieu;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;

    private Integer trang_thai;

    @Temporal(TemporalType.DATE)
    private Date ngay_tao;
    @Temporal(TemporalType.DATE)
    private Date ngay_sua;

    @OneToMany(mappedBy = "idGiamGia")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

}