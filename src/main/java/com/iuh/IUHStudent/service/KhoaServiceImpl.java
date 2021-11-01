package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.exception.KhoaNotFoundException;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KhoaServiceImpl {
    @Autowired
    private KhoaRepository khoaRepository;

    public KhoaVien findKhoaById(int khoaId) {
        Optional<KhoaVien> result = khoaRepository.findById(khoaId);
        KhoaVien khoa = null;
        if (result.isPresent()) {
            khoa = result.get();
        }
        return khoa;
    }

    public boolean deleteKhoa(int khoaId) {
        KhoaVien khoa = findKhoaById(khoaId);
        if(khoa == null){
            throw new KhoaNotFoundException(khoaId);
        }
        khoaRepository.deleteById(khoaId);
        return true;
    }
}
