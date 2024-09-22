package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ma", length = 10)
    private String ma;

    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Column(name = "so_phan_tram_giam")
    private Integer soPhanTramGiam;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Temporal(TemporalType.DATE)
    private Date ngay_tao;
    @Temporal(TemporalType.DATE)
    private Date ngay_sua;

    @OneToMany(mappedBy = "idKhuyenMai")
    private Set<KhuyenMaiChiTiet> khuyenMaiChiTiets = new LinkedHashSet<>();

}