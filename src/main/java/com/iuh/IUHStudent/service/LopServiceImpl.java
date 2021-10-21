package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.exception.LopNotFoundException;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.LopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LopServiceImpl implements LopService {

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Override
    public SinhVien addSinhVienVaoLop(int sinhVienId, int lopId) {
        Lop lop = findLopById(lopId);
        SinhVien sinhVien = sinhVienService.findSinhVienById(sinhVienId);
        if(lop == null) {
            throw new LopNotFoundException(lopId);
        }
        if (sinhVien == null) {
            throw new UserNotFoundException((long) sinhVienId);
        }
        lop.getSinhViens().add(sinhVien);
        lopRepository.save(lop);
        return sinhVien;
    }

    @Override
    public Lop saveLop(Lop lop) {
        return lopRepository.save(lop);
    }


    @Override
    public Lop findLopById(int lopId) {
        Optional<Lop> result = lopRepository.findById(lopId);
        Lop lop = null;
        if(result.isPresent()) {
            lop = result.get();
        }
        return lop;
    }

}
