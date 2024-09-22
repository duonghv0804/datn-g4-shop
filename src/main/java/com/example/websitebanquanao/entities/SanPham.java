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
@Table(name = "san_pham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nationalized
    @Lob
    @Column(name = "ten", nullable = false)
    private String ten;

    @Temporal(TemporalType.DATE)
    private Date ngay_tao;

    @Temporal(TemporalType.DATE)
    private Date ngay_sua;

    private Integer trang_thai;

    @Nationalized
    @Lob
    @Column(name = "anh", nullable = false)
    private String anh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_loai", nullable = false)
    private Loai idLoai;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sex", nullable = false)
    private Sex idSex;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_thuong_hieu", nullable = false)
    private ThuongHieu idThuongHieu;

    @OneToMany(mappedBy = "idSanPham")
    private Set<AnhSanPham> anhSanPhams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSanPham")
    private Set<SanPhamChiTiet> sanPhamChiTiets = new LinkedHashSet<>();
}
