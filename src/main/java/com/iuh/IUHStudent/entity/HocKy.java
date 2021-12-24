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
@Table(name = "hoc_ky")
public class HocKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hocKyId;

    private int soThuTu;

    private String moTa;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "nam_hoc_fk")
    private NamHoc namHoc;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = HocPhan.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "hocKy_fk",referencedColumnName = "hocKyId")
    private Set<HocPhan> hocPhans = new HashSet<>();
}
