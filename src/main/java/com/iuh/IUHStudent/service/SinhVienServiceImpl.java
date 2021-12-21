package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.AccountRepository;
import com.iuh.IUHStudent.repository.SinhVienRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@Service
public class SinhVienServiceImpl implements SinhVienService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private LopService lopService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public SinhVien saveSinhVien(SinhVien sinhVien) {
        sinhVienRepository.save(sinhVien);
        return sinhVien;
    }

    @Override
    public List<SinhVien> findAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    @Override
    public boolean deleteSinhVien(int sinhVienId) {
//        SinhVien sinhVien = findSinhVienById(sinhVienId);
//        System.out.println("sinh vien " + sinhVien);

        Account _account = accountService.findAccountBySinhVienId(sinhVienId);

        if (_account == null) {
            throw new UserNotFoundException((long) sinhVienId);
        }

        accountService.deleteAccount(_account);
        return true;
    }

    @Override
    public SinhVien findSinhVienById(int sinhVienId) {
        Optional<SinhVien> result = sinhVienRepository.findById(sinhVienId);
        SinhVien sinhVien = null;
        if (result.isPresent()) {
            sinhVien = result.get();
        }
        return sinhVien;
    }

    @Override
    public SinhVien findSinhVienByMa(String maSinhVien) {
        Optional<SinhVien> result = sinhVienRepository.findSinhVienByMaSinhVien(maSinhVien);
        SinhVien sinhVien = null;
        if (result.isPresent()) {
            sinhVien = result.get();
        }
        return sinhVien;
    }

    @Override
    public List<SinhVien> findSinhVienByKhoaVienId(int khoaVienId) {
        List<Object[]> sinhViens = sinhVienRepository.getSinhVienWithKhoaVienId(khoaVienId);
        List<SinhVien> sinhViens1 = new ArrayList<>();
        for (Object[] obj :
                sinhViens) {
            SinhVien sinhVien = new SinhVien();
            sinhVien.setSinhVienId((Integer) obj[0]);
            sinhVien.setMaSinhVien((String) obj[1]);
            sinhVien.setMaHoSo((String) obj[2]);
            sinhVien.setImage((String) obj[3]);
            sinhVien.setHoTenDem((String) obj[4]);
            sinhVien.setTen((String) obj[5]);
            sinhVien.setGioiTinh((Boolean) obj[6]);
            sinhVien.setNgaySinh((Date) obj[7]);
            sinhVien.setBacDaoTao((BacDaoTao) obj[8]);
            sinhVien.setTrangThai((TrangThai) obj[9]);
            sinhVien.setLoaiHinhDaoTao((LoaiHinhDaoTao) obj[10]);
            sinhVien.setNgayVaoTruong((Date) obj[11]);
            sinhVien.setNgayVaoDoan((Date) obj[12]);
            sinhVien.setSoDienThoai((String) obj[13]);
            sinhVien.setDiaChi((String) obj[14]);
            sinhVien.setNoiSinh((String) obj[15]);
            sinhVien.setHoKhauThuongTru((String) obj[16]);
            sinhVien.setDanToc((DanToc) obj[17]);
            sinhVien.setNgayVaoDang((Date) obj[18]);
            sinhVien.setEmail((String) obj[19]);
            sinhVien.setTonGiao((TonGiao) obj[20]);


            sinhViens1.add(sinhVien);
        }
        return sinhViens1;
    }

    @Override
    public List<SinhVien> findSinhVienByKhoaVienIdAndNgayVaoTruong(int khoaVienId, String ngayVaoTruong) {
        List<Object[]> sinhViens = sinhVienRepository.getSinhVienWithKhoaVienIdAndNgayVaoTruong(khoaVienId,ngayVaoTruong);
        List<SinhVien> sinhViens1 = new ArrayList<>();
        for (Object[] obj :
                sinhViens) {
            SinhVien sinhVien = new SinhVien();
            sinhVien.setSinhVienId((Integer) obj[0]);
            sinhVien.setMaSinhVien((String) obj[1]);
            sinhVien.setMaHoSo((String) obj[2]);
            sinhVien.setImage((String) obj[3]);
            sinhVien.setHoTenDem((String) obj[4]);
            sinhVien.setTen((String) obj[5]);
            sinhVien.setGioiTinh((Boolean) obj[6]);
            sinhVien.setNgaySinh((Date) obj[7]);
            sinhVien.setBacDaoTao((BacDaoTao) obj[8]);
            sinhVien.setTrangThai((TrangThai) obj[9]);
            sinhVien.setLoaiHinhDaoTao((LoaiHinhDaoTao) obj[10]);
            sinhVien.setNgayVaoTruong((Date) obj[11]);
            sinhVien.setNgayVaoDoan((Date) obj[12]);
            sinhVien.setSoDienThoai((String) obj[13]);
            sinhVien.setDiaChi((String) obj[14]);
            sinhVien.setNoiSinh((String) obj[15]);
            sinhVien.setHoKhauThuongTru((String) obj[16]);
            sinhVien.setDanToc((DanToc) obj[17]);
            sinhVien.setNgayVaoDang((Date) obj[18]);
            sinhVien.setEmail((String) obj[19]);
            sinhVien.setTonGiao((TonGiao) obj[20]);


            sinhViens1.add(sinhVien);
        }
        return sinhViens1;
    }

    @Override
    public List<NamHoc> findNamHocByKhoaVienId(int khoaVienId){
        List<String> sinhViens = sinhVienRepository.getNamHocWithKhoaVienId(khoaVienId);
        List<NamHoc> namHocs = new ArrayList<>();
        for (String obj :
                sinhViens) {
            NamHoc namHoc = new NamHoc();
//            namHoc.setNamHoc(obj);
            namHocs.add(namHoc);
        }
        return namHocs;
    }
    @Override
    public boolean deleteAllSinhVien() {
        accountService.deleteAllAccount();
        AccountInput input = AccountInput.builder()
                .userName("admin")
                .password("admin")
                .build();

        boolean isExistAccount = accountService.exists(input);

        accountRepository.saveAndFlush(Account
                .builder()
                .username(input.getUserName())
                .password(passwordEncoder.encode(input.getPassword()))
                .roles(Set.of("ADMIN"))
                .build());
        return true;
    }

    @Override
    public int getTongSoTinChiOfSinhVien(int sinhVienId) {
        List<Integer> _tongTinChiRes = sinhVienRepository.getTongTinChiBySinhVien(sinhVienId);
        return _tongTinChiRes.get(0);
    }

    @Override
    public int getSoTinChiSinhVienDatDuoc(int sinhVienId) {
        List<Integer> _tinChiDatDuocRes = sinhVienRepository.getSoTinChiSinhVienDatDuoc(sinhVienId);
        return _tinChiDatDuocRes.get(0);
    }

    @Override
    public List<MonHoc> getMonHocOfSinhVienByHocKy(int sinhVienId, int hocKyId) {
        List<Object[]> _monHocs = sinhVienRepository.getListMonHocOfSinhVienByHocKy(sinhVienId,hocKyId);
        List<MonHoc> monHocs = new ArrayList<>();
        for (Object[] obj :
                _monHocs) {
            MonHoc monHoc = new MonHoc();
            monHoc.setTenMonHoc((String) obj[0]);
            monHoc.setSoTinChiLyThuyet((Integer) obj[1]);
            monHoc.setSoTinChiThucHanh((Integer) obj[2]);
            monHocs.add(monHoc);
        }
        return monHocs;
    }
}
