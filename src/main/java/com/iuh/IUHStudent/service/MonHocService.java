package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.ChuyenNganh;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    public  MonHoc findMonHocById(int monHocId) {
        Optional<MonHoc> result = monHocRepository .findById(monHocId);
        MonHoc monHoc = null;
        if (result.isPresent()) {
            monHoc = result.get();
        }
        return monHoc;
    }

    public boolean deleteMonHoc(int monHocId) {
        MonHoc monHoc = findMonHocById(monHocId);
        if(monHoc == null){
            throw new MonHocNotFoundException(monHocId);
        }
        monHocRepository.deleteById(monHocId);
        return true;
    }
    public List<MonHoc> getMonHocWithName(String tenMonHoc) {
        List<Object[]> monHocs =  monHocRepository.getMonHocWithName(tenMonHoc);
        List<MonHoc> _monHocs = new ArrayList<>();
        for (Object[] obj :
                monHocs) {
            System.out.println("test " + obj.toString());
            MonHoc _monHoc = new MonHoc();
            _monHoc.setMonHocId((Integer) obj[0]);
            _monHoc.setTenMonHoc((String) obj[1]);
            _monHoc.setMoTa((String) obj[2]);

            _monHocs.add(_monHoc);
        }


        return _monHocs;
    }


}
