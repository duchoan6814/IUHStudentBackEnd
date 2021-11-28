package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sinhVienId;

    private String maSinhVien;
    private String maHoSo;
    private String image;
    private String hoTenDem;
    private String ten;
    private boolean gioiTinh;
    private BacDaoTao bacDaoTao;
    private TrangThai trangThai;
    private LoaiHinhDaoTao loaiHinhDaoTao;
    private Date ngayVaoTruong;
    private Date ngaySinh;
    private Date ngayVaoDoan;
    private String soDienThoai;
    private String diaChi;
    private String noiSinh;
    private String hoKhauThuongTru;
    private DanToc danToc;
    private Date ngayVaoDang;
    private String email;
    private TonGiao tonGiao;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SinhVienLopHocPhan> lopHocPhans;

    @OneToOne(mappedBy = "sinhVien")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "lop_fk")
    private Lop lop;

    @Override
    public String toString() {
        return "SinhVien{" +
                "sinhVienId=" + sinhVienId +
                ", maSinhVien='" + maSinhVien + '\'' +
                ", maHoSo='" + maHoSo + '\'' +
                ", image='" + image + '\'' +
                ", hoTenDem='" + hoTenDem + '\'' +
                ", ten='" + ten + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", bacDaoTao=" + bacDaoTao +
                ", trangThai=" + trangThai +
                ", loaiHinhDaoTao=" + loaiHinhDaoTao +
                ", ngayVaoTruong=" + ngayVaoTruong +
                ", ngaySinh=" + ngaySinh +
                ", ngayVaoDoan=" + ngayVaoDoan +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", noiSinh='" + noiSinh + '\'' +
                ", hoKhauThuongTru='" + hoKhauThuongTru + '\'' +
                ", danToc=" + danToc +
                ", ngayVaoDang=" + ngayVaoDang +
                ", email='" + email + '\'' +
                ", tonGiao=" + tonGiao +
                '}';
    }
}
