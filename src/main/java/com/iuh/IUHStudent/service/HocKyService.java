package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.exception.HocKyNotFoundException;
import com.iuh.IUHStudent.exception.KhoaNotFoundException;
import com.iuh.IUHStudent.repository.HocKyRepository;
import com.iuh.IUHStudent.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class HocKyService {
    @Autowired
    private HocKyRepository hocKyRepository;

    public HocKy findKhoaById(int hocKyId) {
        Optional<HocKy> result = hocKyRepository.findById(hocKyId);
        HocKy hocKy = null;
        if (result.isPresent()) {
            hocKy = result.get();
        }
        return hocKy;
    }

    public boolean deleteKhoa(int hocKyId) {
        HocKy hocKy = findKhoaById(hocKyId);
        if(hocKy == null){
            throw new HocKyNotFoundException(hocKyId);
        }
        hocKyRepository.deleteById(hocKyId);
        return true;
    }
}
