package com.iuh.IUHStudent.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("lopHocPhanId")
    @JoinColumn(name = "lopHocPhanId")
    private LopHocPhan lopHocPhan;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Double> diemThuongKy;

    private Double diemGiuaKy;
    private Double diemCuoiKy;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Double> diemThucHanh;
    private String ghiChu;
}
