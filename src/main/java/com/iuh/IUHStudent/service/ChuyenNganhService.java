package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.exception.ChuyenNganhNotFoundException;
import com.iuh.IUHStudent.repository.ChuyenNganhRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChuyenNganhService {
    @Autowired
    private ChuyenNganhRespository chuyenNganhRespository;

    public ChuyenNganh findChuyenNganhById(int chyenNganhId) {
        Optional<ChuyenNganh> result = chuyenNganhRespository.findById(chyenNganhId);
        ChuyenNganh chuyenNganh = null;
        if (result.isPresent()) {
            chuyenNganh = result.get();
        }
        return chuyenNganh;
    }
    public boolean deleteChuyenNganh(int chuyenNganhId) {
        ChuyenNganh chuyenNganh = findChuyenNganhById(chuyenNganhId);
        if(chuyenNganh == null){
            throw new ChuyenNganhNotFoundException(chuyenNganhId);
        }
        chuyenNganhRespository.deleteById(chuyenNganhId);
        return true;
    }
    public List<ChuyenNganh> getChuyenNganhByKhoaVienId(int khoaVienId) {
        List<ChuyenNganh> chuyenNganhs = (List<ChuyenNganh>) chuyenNganhRespository.findAllActiveUsers(khoaVienId);
        return chuyenNganhs;
    }

}
