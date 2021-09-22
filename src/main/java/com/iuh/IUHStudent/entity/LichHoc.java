package com.app.SupportStudentApp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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

    @ManyToOne
    @JoinColumn(name = "lopHocPhan_fk")
    private LopHocPhan lopHocPhan;

    @ManyToOne
    @JoinColumn(name = "phongHoc_fk")
    private PhongHoc phongHoc;
}
