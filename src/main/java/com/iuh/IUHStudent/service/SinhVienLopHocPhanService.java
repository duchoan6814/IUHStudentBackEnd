package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.SinhVienLopHocPhan;
import com.iuh.IUHStudent.entity.SinhVienLopHocPhanPK;
import com.iuh.IUHStudent.repository.SinhVienLopHocPhanRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SinhVienLopHocPhanService {
    @Autowired
    private SinhVienLopHocPhanRespository sinhVienLopHocPhanRespository;

    public List<SinhVienLopHocPhan> getSinhVienLopHocPhanByHocKy(int sinhVienID, int hocKyId) {
        List<Object[]> _objects = sinhVienLopHocPhanRespository.getLopHocPhanOfSinhVienByHocKy(sinhVienID, hocKyId);

        List<SinhVienLopHocPhan> _sinhVienLopHocPhans = new ArrayList<>();

        for (Object[] _obj : _objects) {
            Optional<SinhVienLopHocPhan> _sinhVienLopHocPhan = sinhVienLopHocPhanRespository.findById(SinhVienLopHocPhanPK.builder()
                    .sinhVienId(sinhVienID)
                    .lopHocPhanId((Integer) _obj[0])
                    .build());

            _sinhVienLopHocPhans.add(_sinhVienLopHocPhan.get());
        }

        return _sinhVienLopHocPhans;
    }

}
