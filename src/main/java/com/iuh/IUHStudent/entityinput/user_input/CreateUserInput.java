package com.iuh.IUHStudent.entityinput.user_input;

import com.iuh.IUHStudent.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class CreateSinhVienInput {
    private String maSinhVien;
    private String maHoSo;
    private List<String> images;
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
    private Set<LopHocPhan> lopHocPhans;
    private Lop lop;
}
