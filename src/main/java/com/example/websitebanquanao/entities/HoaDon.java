package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ma", nullable = false, length = 50)
    private String ma;

    @Column(name = "ngay_thanh_toan")
    private Instant ngayThanhToan;

    @Column(name = "ngay_van_chuyen")
    private Instant ngayVanChuyen;

    @Column(name = "ngay_nhan")
    private Instant ngayNhan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang idKhachHang;


    @ManyToOne
    @JoinColumn(name = "id_hinh_thuc_thanh_toan", referencedColumnName = "id")
    private HinhThucThanhToan hinhThucThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien idNhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_giam_gia")
    private GiamGia idGiamGia;

    @Nationalized
    @Column(name = "nguoi_nhan", length = 100)
    private String nguoiNhan;

    @Nationalized
    @Column(name = "dia_chi", length = 100)
    private String diaChi;

    @Nationalized
    @Column(name = "email", length = 50)
    private String email;

    @Nationalized
    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @Column(name = "tong_tien", precision = 20)
    private BigDecimal tongTien;

    @Column(name = "so_tien_giam", precision = 20)
    private BigDecimal tienGiam;

    @Column(name = "thanh_toan", precision = 20)
    private BigDecimal thanhToan;

    @Nationalized
    @Column(name = "xa_phuong", length = 80)
    private String xaPhuong;

    @Nationalized
    @Column(name = "quan_huyen", length = 80)
    private String quanHuyen;

    @Nationalized
    @Column(name = "tinh_thanh_pho", length = 80)
    private String tinhThanhPho;

    @Column(name = "loai_hoa_don")
    private Integer loaiHoaDon;

    @Column(name = "ma_van_chuyen", nullable = false, length = 50)
    private String maVanChuyen;

    @Column(name = "ten_don_vi_van_chuyen", nullable = false)
    private String tenDonViVanChuyen;

    @Column(name = "phi_van_chuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "anh_hoa_don_chuyen_khoan")
    private String anhHoaDonChuyenKhoan;

    @OneToMany(mappedBy = "idHoaDon")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();


    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_tao")
    private Instant ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

}