package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "lich_hoc")
public class LichHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lichHocId;

    private String loaiHocPhan;
    private int ngayHocTrongTuan;
    private int nhomThucHanh;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;
    private int tietHocBatDau;
    private int tietHocKetThuc;
    private String ghiChu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lopHocPhan_fk")
    private LopHocPhan lopHocPhan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phongHoc_fk")
    private PhongHoc phongHoc;
}
