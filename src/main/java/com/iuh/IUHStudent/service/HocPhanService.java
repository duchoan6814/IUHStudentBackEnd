package com.iuh.IUHStudent.service;


import com.iuh.IUHStudent.entity.HocPhan;
import com.iuh.IUHStudent.exception.HocPhanNotFoundException;
import com.iuh.IUHStudent.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class HocPhanService {
    @Autowired
    private HocPhanRepository hocPhanRespository;

    public HocPhan findHocPhanById(int hocPhanId) {
        Optional<HocPhan> result = hocPhanRespository.findById(hocPhanId);
        HocPhan hocPhan = null;
        if (result.isPresent()) {
            hocPhan = result.get();
        }
        return hocPhan;
    }

    public boolean deleteHocPhan(int hocPhanId) {
        HocPhan hocPhan = findHocPhanById(hocPhanId);
        if (hocPhan == null) {
            throw new HocPhanNotFoundException(hocPhanId);
        }
        hocPhanRespository.deleteById(hocPhanId);
        return true;
    }
}
