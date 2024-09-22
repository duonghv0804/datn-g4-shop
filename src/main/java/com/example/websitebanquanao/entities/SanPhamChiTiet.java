package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ma_san_pham", length = 20)
    private String maSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kich_co")
    private KichCo idKichCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mau_sac")
    private MauSac idMauSac;

    @Column(name = "gia", precision = 20)
    private BigDecimal gia;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Nationalized
    @Lob
    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Temporal(TemporalType.DATE)
    private Date ngay_tao;

    @Temporal(TemporalType.DATE)
    private Date ngay_sua;

    @OneToMany(mappedBy = "idSanPhamChiTiet")
    private Set<GioHangChiTiet> gioHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPhamChiTiet")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPhamChiTiet")
    private Set<KhuyenMaiChiTiet> khuyenMaiChiTiets = new LinkedHashSet<>();
}
