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
@Table(name = "hoc_phan")
public class HocPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hocPhanId;

    private String maHocPhan;
    private String soTinChiLyThuyet;
    private String getSoTinChiThucHanh;
    private String moTa;
    private boolean batBuoc;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = LopHocPhan.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "hocPhan_fk",referencedColumnName = "hocPhanId")
    private Set<LopHocPhan> lopHocPhans = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hocKy_fk")
    private HocKy hocKy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monHocTienQuyet_fk")
    private MonHoc monHocTienQuyet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monHocSongHanh_fk")
    private MonHoc monHocSongHanh;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monHocTruoc_fk")
    private MonHoc monHocTruoc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monHoc_fk")
    private MonHoc monHoc;
}
