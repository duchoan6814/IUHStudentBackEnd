package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.entity.MonHoc;
import com.iuh.IUHStudent.exception.HocKyNotFoundException;
import com.iuh.IUHStudent.exception.KhoaNotFoundException;
import com.iuh.IUHStudent.exception.MonHocNotFoundException;
import com.iuh.IUHStudent.repository.HocKyRepository;
import com.iuh.IUHStudent.repository.KhoaRepository;
import com.iuh.IUHStudent.repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    public  MonHoc findKhoaById(int monHocId) {
        Optional<MonHoc> result = monHocRepository .findById(monHocId);
        MonHoc monHoc = null;
        if (result.isPresent()) {
            monHoc = result.get();
        }
        return monHoc;
    }

    public boolean deleteKhoa(int monHocId) {
        MonHoc monHoc = findKhoaById(monHocId);
        if(monHoc == null){
            throw new KhoaNotFoundException(monHocId);
        }
        monHocRepository.deleteById(monHocId);
        return true;
    }
}
