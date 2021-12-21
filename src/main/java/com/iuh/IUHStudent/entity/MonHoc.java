package com.iuh.IUHStudent.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "mon_hoc")
public class MonHoc  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int monHocId;

    private String tenMonHoc;
    private String moTa;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaVien_fk")
    private KhoaVien khoaVien;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = HocPhan.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "monHoc_fk",referencedColumnName = "monHocId")
    private Set<HocPhan> hocPhans;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "chuyen_nganh_mon_hoc",
            joinColumns = @JoinColumn(name = "monHocId"),
            inverseJoinColumns = @JoinColumn(name = "chuyenNganhId"))
    @JsonManagedReference
    private Set<ChuyenNganh> chuyenNganhs = new HashSet<>();

    public int soTinChi() {
        return soTinChiLyThuyet + soTinChiThucHanh;
    }

}
