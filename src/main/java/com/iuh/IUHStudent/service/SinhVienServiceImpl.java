package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SinhVienServiceImpl implements SinhVienService{
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Override
    public SinhVien saveSinhVien(SinhVien sinhVien) {
        sinhVienRepository.save(sinhVien);
        return sinhVien;
    }

    @Override
    public List<SinhVien> findAllSinhVien() {

        return sinhVienRepository.findAll();
    }

    @Override
    public void deleteSinhVien(int sinhVienId) {
        sinhVienRepository.deleteById(sinhVienId);
    }

    @Override
    public SinhVien findSinhVienById(int sinhVienId) {
        Optional<SinhVien> result = sinhVienRepository.findById(sinhVienId);
        SinhVien sinhVien = null;
        if(result.isPresent()) {
            sinhVien = result.get();
        }
        return sinhVien;
    }

}
