package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.SinhVien;

import java.util.List;

public interface SinhVienService {
    SinhVien saveSinhVien(SinhVien sinhVien);
    List<SinhVien> findAllSinhVien();
    void deleteSinhVien(int sinhVienId);
    SinhVien findSinhVienById(int sinhVienId);

}
