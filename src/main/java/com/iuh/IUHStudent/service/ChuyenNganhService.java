package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.exception.ChuyenNganhNotFoundException;
import com.iuh.IUHStudent.repository.ChuyenNganhRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        List<Object[]> chuyenNganhs =  chuyenNganhRespository.findAllActiveUsers(khoaVienId);
        List<ChuyenNganh> _chuyenNganhs = new ArrayList<>();
        for (Object[] obj :
                chuyenNganhs) {
           ChuyenNganh _chuyenNganh = new ChuyenNganh();
           _chuyenNganh.setChuyenNganhId((Integer) obj[0]);
           _chuyenNganh.setTenChuyenNganh((String) obj[1]);

           _chuyenNganhs.add(_chuyenNganh);
        }


        return _chuyenNganhs;
    }

}
