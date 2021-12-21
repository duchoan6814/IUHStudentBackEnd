package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.NamHoc;
import com.iuh.IUHStudent.entity.SinhVien;

import java.text.ParseException;
import java.util.List;

public interface SinhVienService {
    SinhVien saveSinhVien(SinhVien sinhVien);
    List<SinhVien> findAllSinhVien();
    boolean deleteSinhVien(int sinhVienId);
    SinhVien findSinhVienById(int sinhVienId);
    SinhVien findSinhVienByMa(String maSinhVien);
    List<SinhVien> findSinhVienByKhoaVienId(int khoaVienId);
    List<SinhVien> findSinhVienByKhoaVienIdAndNgayVaoTruong(int khoaVienId, String ngayVaoTruong);
    List<NamHoc> findNamHocByKhoaVienId(int khoaVienId) throws ParseException;
    boolean deleteAllSinhVien();
    int getTongSoTinChiOfSinhVien(int sinhVienId);
    int getSoTinChiSinhVienDatDuoc(int sinhVienId);
}
