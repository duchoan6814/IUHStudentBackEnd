package com.iuh.IUHStudent.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class    LopHocPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lopHocPhanId;

    private String maLopHocPhan;
    private String tenVietTat;
    private String tenLopHocPhan;
    private int soNhomThucHanh;
    private TrangThaiLopHocPhan trangThaiLopHocPhan;
    private int soLuongToiDa;
    private String moTa;

    @OneToMany(mappedBy = "lopHocPhan" , cascade = CascadeType.ALL)
    private List<SinhVienLopHocPhan> sinhViens;

    @ManyToOne
    @JoinColumn(name = "hocPhan_fk")
    private HocPhan hocPhan;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "giangvien_lophocphan",
            joinColumns = @JoinColumn(name = "lopHocPhanId"),
            inverseJoinColumns = @JoinColumn(name = "giangVienId"))
    @JsonManagedReference
    private Set<GiangVien> giangViens = new HashSet<>();

}
