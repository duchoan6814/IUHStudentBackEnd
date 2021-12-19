package com.iuh.IUHStudent.service;


import com.iuh.IUHStudent.entity.HocPhan;
import com.iuh.IUHStudent.entity.LopHocPhan;
import com.iuh.IUHStudent.entity.TrangThaiLopHocPhan;
import com.iuh.IUHStudent.exception.HocPhanNotFoundException;
import com.iuh.IUHStudent.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<HocPhan> getHocPhanByKhoaVienId(int khoaVienId) {
        List<Object[]> hps =  hocPhanRespository.findHocPhanByKhoaVienId(khoaVienId);
        List<HocPhan> _hps = new ArrayList<>();
        for (Object[] obj :
                hps) {
            HocPhan _hp = new HocPhan();
            _hp.setHocPhanId((Integer) obj[0]);
            _hp.setMaHocPhan((String) obj[1]);
            _hp.setSoTinChiLyThuyet((Integer) obj[2]);
            _hp.setGetSoTinChiThucHanh((Integer) obj[3]);
            _hp.setMoTa((String) obj[4]);
            _hp.setBatBuoc((boolean) obj[5]);

            _hps.add(_hp);
        }

        return _hps;
    }
}
