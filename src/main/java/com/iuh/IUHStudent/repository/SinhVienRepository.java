package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SinhVienRepository extends JpaRepository<SinhVien,Integer> {
    Optional<SinhVien> findSinhVienByMaSinhVien(String maSinhVien);
}
