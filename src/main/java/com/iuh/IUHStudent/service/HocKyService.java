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

    public HocKy findHcoKyById(int hocKyId) {
        Optional<HocKy> result = hocKyRepository.findById(hocKyId);
        HocKy hocKy = null;
        if (result.isPresent()) {
            hocKy = result.get();
        }
        return hocKy;
    }

    public boolean deleteHocKy(int hocKyId) {
        HocKy hocKy = findHcoKyById(hocKyId);
        if(hocKy == null){
            throw new HocKyNotFoundException(hocKyId);
        }
        hocKyRepository.deleteById(hocKyId);
        return true;
    }
}
