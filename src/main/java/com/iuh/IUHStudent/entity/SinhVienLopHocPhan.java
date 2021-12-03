package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sinhvien_lophocphan")
public class SinhVienLopHocPhan {
    @EmbeddedId
    private SinhVienLopHocPhanPK id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("sinhVienId")
    @JoinColumn(name = "sinhVienId")
    private SinhVien sinhVien;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("lopHocPhanId")
    @JoinColumn(name = "lopHocPhanId")
    private LopHocPhan lopHocPhan;

    @ElementCollection
    private List<Double> diemThuongKy;

    private double diemGiuaKy;
    private double diemCuoiKy;

    @ElementCollection
    private List<Double> diemThucHanh;
    private String ghiChu;
}
