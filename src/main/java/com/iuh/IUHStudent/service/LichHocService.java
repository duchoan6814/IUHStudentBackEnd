package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.repository.LichHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LichHocService {

    @Autowired
    private LichHocRepository lichHocRepository;

    public List<LichHoc> getLichHocBySinhVienId(int sinhVienID, Date fistDateOfWeek, Date lastDateOfWeek) {
        List<Object[]> _res = lichHocRepository.getListLichHocBySinhVien(sinhVienID, fistDateOfWeek, lastDateOfWeek);

        List<LichHoc> _lichHocs = new ArrayList<>();

        for (Object[] obj :
                _res) {

            Set<GiangVien> _giangViens = new HashSet<>();

            _giangViens.add(GiangVien.builder()
                    .hoTenDem((String) obj[11])
                    .ten((String) obj[12])
                    .build());

            MonHoc _monHoc = MonHoc.builder()
                    .tenMonHoc((String) obj[14])
                    .build();

            HocPhan _hocPhan = HocPhan.builder()
                    .monHoc(_monHoc)
                    .build();

            LopHocPhan _lopHocPhan = LopHocPhan.builder()
                    .maLopHocPhan((String) obj[7])
                    .tenLopHocPhan((String) obj[8])
                    .giangViens(_giangViens)
                    .hocPhan(_hocPhan)
                    .build();

            DayNha _dayNha = DayNha.builder()
                    .tenDayNha((String) obj[10])
                    .build();

            PhongHoc _phongHoc = PhongHoc.builder()
                    .tenPhongHoc((String) obj[9])
                    .dayNha(_dayNha)
                    .build();

            LichHoc _lichHoc;

            if(obj[13] != null) {
                _lichHoc =  LichHoc.builder()
                        .lichHocId((Integer) obj[0])
                        .ghiChu((String) obj[1])
                        .ngayHocTrongTuan((Integer) obj[2])
                        .tietHocBatDau((Integer) obj[5])
                        .tietHocKetThuc((Integer) obj[6])
                        .lopHocPhan(_lopHocPhan)
                        .phongHoc(_phongHoc)
                        .nhomThucHanh((Integer) obj[13])
                        .build();
            }else {
                _lichHoc =  LichHoc.builder()
                        .lichHocId((Integer) obj[0])
                        .ghiChu((String) obj[1])
                        .ngayHocTrongTuan((Integer) obj[2])
                        .tietHocBatDau((Integer) obj[5])
                        .tietHocKetThuc((Integer) obj[6])
                        .lopHocPhan(_lopHocPhan)
                        .phongHoc(_phongHoc)
                        .build();
            }


            _lichHocs.add(_lichHoc);
        }

        return _lichHocs;
    }

}
