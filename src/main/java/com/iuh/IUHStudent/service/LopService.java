package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.entity.SinhVien;

public interface LopService {
    Lop findLopById(int lopId);
    SinhVien addSinhVienVaoLop(int sinhVienId, int lopId);
    Lop saveLop(Lop lop);
}
