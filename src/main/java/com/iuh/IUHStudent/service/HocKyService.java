package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.entity.NamHoc;
import com.iuh.IUHStudent.exception.HocKyNotFoundException;
import com.iuh.IUHStudent.exception.KhoaNotFoundException;
import com.iuh.IUHStudent.repository.HocKyRepository;
import com.iuh.IUHStudent.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HocKyService {
    @Autowired
    private HocKyRepository hocKyRepository;

    public List<HocKy> findHocKyBySinhVienId(int sinhVienId) throws ParseException {
        List<Object[]> _res = hocKyRepository.findHocKyBySinhVien(sinhVienId);

        List<HocKy> _hocKys = new ArrayList<>();

        for (Object[] _i : _res) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            NamHoc _namHoc = NamHoc.builder()
                    .startDate(df.parse(_i[4].toString()))
                    .endDate(df.parse(_i[3].toString()))
                    .build();

            HocKy _hocKy = HocKy.builder()
                    .hocKyId((Integer) _i[0])
                    .soThuTu((Integer) _i[1])
                    .moTa((String) _i[2])
                    .namHoc(_namHoc)
                    .build();

            _hocKys.add(_hocKy);
        }

        return _hocKys;
    }

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
        if (hocKy == null) {
            throw new HocKyNotFoundException(hocKyId);
        }
        hocKyRepository.deleteById(hocKyId);
        return true;
    }
}
