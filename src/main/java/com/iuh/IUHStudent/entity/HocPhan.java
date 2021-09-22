package com.app.SupportStudentApp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    @ManyToOne
    @JoinColumn(name = "hocKy_fk")
    private HocKy hocKy;
}
