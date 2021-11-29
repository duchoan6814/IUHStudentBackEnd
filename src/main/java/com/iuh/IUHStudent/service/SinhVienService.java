package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.NamHoc;
import com.iuh.IUHStudent.entity.SinhVien;

import java.util.List;

public interface SinhVienService {
    SinhVien saveSinhVien(SinhVien sinhVien);
    List<SinhVien> findAllSinhVien();
    boolean deleteSinhVien(int sinhVienId);
    SinhVien findSinhVienById(int sinhVienId);
    SinhVien findSinhVienByMa(String maSinhVien);
    List<SinhVien> finSinhVienByKhoaVienId(int khoaVienId);
    List<SinhVien> finSinhVienByKhoaVienIdAndNgayVaoTruong(int khoaVienId, String ngayVaoTruong);
    List<NamHoc> finNamHocByKhoaVienId(int khoaVienId);
}
