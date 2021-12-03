package com.iuh.IUHStudent.entityinput;

import com.iuh.IUHStudent.entity.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SinhVienInput {
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
}
