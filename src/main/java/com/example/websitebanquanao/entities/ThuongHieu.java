package com.example.websitebanquanao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "thuong_hieu")
@AllArgsConstructor
@NoArgsConstructor
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "ten", nullable = false, length = 50)
    private String ten;

    @Temporal(TemporalType.DATE)
    private Date ngay_tao;

    @Temporal(TemporalType.DATE)
    private Date ngay_sua;

    private int trang_thai;

    @OneToMany(mappedBy = "idThuongHieu")
    private Set<SanPham> sanPhams = new LinkedHashSet<>();

}