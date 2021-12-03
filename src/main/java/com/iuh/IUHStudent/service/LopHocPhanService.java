package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.entity.LopHocPhan;
import com.iuh.IUHStudent.entity.TrangThaiLopHocPhan;
import com.iuh.IUHStudent.exception.ChuyenNganhNotFoundException;
import com.iuh.IUHStudent.exception.LopHocPhanNotFoundException;
import com.iuh.IUHStudent.repository.LopHocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<LopHocPhan> getLopHocPhanByKhoaVienId(int khoaVienId) {
        List<Object[]> lhps =  lopHocPhanRespository.findLopHocPhanByKhoaVienId(khoaVienId);
        List<LopHocPhan> _lhps = new ArrayList<>();
        for (Object[] obj :
                lhps) {
            LopHocPhan _lhp = new LopHocPhan();
            _lhp.setLopHocPhanId((Integer) obj[0]);
            _lhp.setMaLopHocPhan((String) obj[1]);
            _lhp.setTenVietTat((String) obj[2]);
            _lhp.setTenLopHocPhan((String) obj[3]);
            _lhp.setSoNhomThucHanh((Integer) obj[4]);
            _lhp.setTrangThaiLopHocPhan((TrangThaiLopHocPhan) obj[5]);
            _lhp.setSoLuongToiDa((Integer) obj[6]);
            _lhp.setMoTa((String) obj[7]);

            _lhps.add(_lhp);
        }


        return _lhps;
    }
}
