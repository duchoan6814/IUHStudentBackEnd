package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "lop")
public class Lop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lopId;

    private String tenLop;
    private String khoaHoc;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = SinhVien.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "lop_fk",referencedColumnName = "lopId")
    private Set<SinhVien> sinhViens = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chuyenNganh_fk")
    private ChuyenNganh chuyenNganh;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "giangVien_fk")
    private GiangVien giangVien;
}
