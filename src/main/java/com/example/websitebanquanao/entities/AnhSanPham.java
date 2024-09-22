package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "anh_san_pham")
public class AnhSanPham {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham")
    private SanPham idSanPham;

    @Nationalized
    @Lob
    @Column(name = "duong_dan", nullable = false)
    private String duongDan;

    @Column(name = "trang_thai")
    private Integer trangThai;

}