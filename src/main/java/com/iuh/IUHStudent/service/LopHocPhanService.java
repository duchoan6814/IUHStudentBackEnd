package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.entity.LopHocPhan;
import com.iuh.IUHStudent.exception.ChuyenNganhNotFoundException;
import com.iuh.IUHStudent.exception.LopHocPhanNotFoundException;
import com.iuh.IUHStudent.repository.LopHocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LopHocPhanService {
    @Autowired
    private LopHocPhanRepository lopHocPhanRespository;

    public LopHocPhan findLopHocPhanById(int lopHocPhanId) {
        Optional<LopHocPhan> result = lopHocPhanRespository.findById(lopHocPhanId);
        LopHocPhan lopHocPhan = null;
        if (result.isPresent()) {
            lopHocPhan = result.get();
        }
        return lopHocPhan;
    }
    public boolean deleteLopHocPhan(int lopHocPhanId) {
        LopHocPhan lopHocPhan = findLopHocPhanById(lopHocPhanId);
        if(lopHocPhan == null){
            throw new LopHocPhanNotFoundException(lopHocPhanId);
        }
        lopHocPhanRespository.deleteById(lopHocPhanId);
        return true;
    }
}
