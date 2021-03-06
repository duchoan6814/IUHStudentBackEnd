package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.repository.ChuyenNganhRespository;
import com.iuh.IUHStudent.repository.KhoaRepository;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.repository.SinhVienRepository;

import com.iuh.IUHStudent.repository.*;

import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.response.diem.DiemHocKy;
import com.iuh.IUHStudent.response.diem.DiemMonHoc;
import com.iuh.IUHStudent.response.diem.DiemThiResponse;
import com.iuh.IUHStudent.response.hocKy.HocKySimple;
import com.iuh.IUHStudent.response.hocKy.HocKySimpleResponse;
import com.iuh.IUHStudent.response.ketQuaHocTap.KetQuaHocTap;
import com.iuh.IUHStudent.response.ketQuaHocTap.KetQuaHocTapResponse;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.khoa.KhoasResponse;
import com.iuh.IUHStudent.response.lichHoc.DayOfWeek;
import com.iuh.IUHStudent.response.lichHoc.LichHocRes;
import com.iuh.IUHStudent.response.lichHoc.LichHocResponse;
import com.iuh.IUHStudent.response.namHoc.NamHocAllResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhVienResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhViensResponse;
import com.iuh.IUHStudent.response.tienDoHocTap.TienDoHocTap;
import com.iuh.IUHStudent.response.tienDoHocTap.TienDoHocTapResponse;
import com.iuh.IUHStudent.service.*;
import com.iuh.IUHStudent.util.Helper;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private KhoaRepository khoaRepository;

    @Autowired
    public KhoaService khoaService;

    @Autowired
    private HocKyRepository hocKyRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private ChuyenNganhRespository chuyenNganhRespository;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private MonHocService monHocService;

    @Autowired
    LichHocService lichHocService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    SinhVienLopHocPhanService sinhVienLopHocPhanService;

    @Autowired
    private NamHocRepository namHocRepository;

    @Autowired
    private NamHocService namHocService;

    @PreAuthorize("hasAnyAuthority('USER')")
    public HocKySimpleResponse getHocKySimple() {
        Account _c = accountService.getCurrentAccount();
        DateFormat _df = new SimpleDateFormat("YYYY");

        try {
            List<HocKy> _listHocKy = hocKyService.findHocKyBySinhVienId(_c.getSinhVien().getSinhVienId());

            List<HocKySimple> _listHocKySimple = new ArrayList<>();

            _listHocKy.forEach(i -> {
                String _startYear = _df.format(i.getNamHoc().getStartDate());
                String _endYear = _df.format(i.getNamHoc().getEndDate());
                String _hocKy = "H???c k??? " + i.getSoThuTu();

                _listHocKySimple.add(HocKySimple.builder()
                        .hocKyId(i.getHocKyId())
                        .namHoc(_hocKy + " (" + _startYear + "-" + _endYear + ")")
                        .build());
            });

            return HocKySimpleResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin h???c k??? th??nh c??ng")
                    .data(_listHocKySimple)
                    .build();

        } catch (ParseException e) {
            e.printStackTrace();
            return HocKySimpleResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin h???c k??? kh??ng th??nh c??ng")
                    .errors(Arrays.asList(ErrorsResponse.builder()
                            .message("L???i h??? th???ng").build()))
                    .build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public NamHocAllResponse getNamHoc(String startDate, String endDate) {
        if (startDate == null && endDate == null) {

            List<NamHoc> _listNamHoc = namHocService.getAllNamHoc();

            return NamHocAllResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin n??m h???c th??nh c??ng.")
                    .data(_listNamHoc)
                    .build();
        }

        if (startDate != null && endDate == null) {
            try {
                DateTime _startDate = new DateTime(startDate);

                List<NamHoc> _listNamHoc = namHocService.getNamHocWithStartDate(_startDate.toDate());

                return NamHocAllResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("L???y th??ng tin n??m h???c th??nh c??ng.")
                        .data(_listNamHoc)
                        .build();

            } catch (Exception ex) {
                return NamHocAllResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("L???y th??ng tin n??m h???c kh??ng th??nh c??ng.")
                        .errors(Arrays.asList(
                                ErrorsResponse.builder()
                                        .message("Nh???p kh??ng ????ng ?????nh d???ng th???i gian")
                                        .build()
                        ))
                        .build();
            }

        }

        if (startDate == null && endDate != null) {
            try {
                DateTime _endDate = new DateTime(endDate);

                List<NamHoc> _listNamHoc = namHocService.getNamHocWithEndDate(_endDate.toDate());

                return NamHocAllResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("L???y th??ng tin n??m h???c th??nh c??ng.")
                        .data(_listNamHoc)
                        .build();

            } catch (Exception ex) {
                return NamHocAllResponse.builder()
                        .status(ResponseStatus.OK)
                        .message("L???y th??ng tin n??m h???c kh??ng th??nh c??ng.")
                        .errors(Arrays.asList(
                                ErrorsResponse.builder()
                                        .message("Nh???p kh??ng ????ng ?????nh d???ng th???i gian")
                                        .build()
                        ))
                        .build();
            }
        }

        try {
            DateTime _endDate = new DateTime(endDate);
            DateTime _startDate = new DateTime(startDate);

            List<NamHoc> _listNamHoc = namHocService.getNamHocBetweenDate(_startDate.toDate(), _endDate.toDate());

            return NamHocAllResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin n??m h???c th??nh c??ng.")
                    .data(_listNamHoc)
                    .build();

        } catch (Exception ex) {
            return NamHocAllResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin n??m h???c kh??ng th??nh c??ng.")
                    .errors(Arrays.asList(
                            ErrorsResponse.builder()
                                    .message("Nh???p kh??ng ????ng ?????nh d???ng th???i gian")
                                    .build()
                    ))
                    .build();
        }

    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public KetQuaHocTapResponse getKetQuaHocTap(int hocKyId) {
        Account currentAccount = accountService.getCurrentAccount();

        List<SinhVienLopHocPhan> _sinhVienLopHocPhans = sinhVienLopHocPhanService.getSinhVienLopHocPhanByHocKy(currentAccount.getSinhVien().getSinhVienId(), hocKyId);

        List<KetQuaHocTap> _ketQuaHocTaps = new ArrayList<>();

        for (SinhVienLopHocPhan sinhVienLopHocPhan : _sinhVienLopHocPhans) {
            LopHocPhan _lopHocPhan = lopHocPhanService.findLopHocPhanById(sinhVienLopHocPhan.getLopHocPhan().getLopHocPhanId());

            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = sinhVienLopHocPhanService.getListSinhVienLopHocPhanByHocPhan(_lopHocPhan.getLopHocPhanId());


            Double _diemTrungBinh = _listSinhVienLopHocPhan.stream().mapToDouble(a -> {
                if(a.getDiemCuoiKy() == null) return 0;

                return Helper.tinhDiemTrungBinhh(a);
            }).average().getAsDouble();

            KetQuaHocTap _ketQuaHocTap;

            if(sinhVienLopHocPhan.getDiemCuoiKy() == null) {
                _ketQuaHocTap = KetQuaHocTap.builder()
                        .monHoc(sinhVienLopHocPhan.getLopHocPhan().getHocPhan().getMonHoc())
                        .diemTrungBinh(_diemTrungBinh)
                        .build();
            }else {
                _ketQuaHocTap =KetQuaHocTap.builder()
                        .monHoc(sinhVienLopHocPhan.getLopHocPhan().getHocPhan().getMonHoc())
                        .diem(Helper.tinhDiemTrungBinhh(sinhVienLopHocPhan))
                        .diemTrungBinh(_diemTrungBinh)
                        .build();

            }


            _ketQuaHocTaps.add(_ketQuaHocTap);
        }

        return KetQuaHocTapResponse.builder()
                .status(ResponseStatus.OK)
                .message("L???y th??ng tin k???t qu??? h???c t???p th??nh c??ng.")
                .data(_ketQuaHocTaps)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public TienDoHocTapResponse getTienDoHocTap() {
        Account _currentAccount = accountService.getCurrentAccount();

        int _tongTinChi = sinhVienService.getTongSoTinChiOfSinhVien(_currentAccount.getSinhVien().getSinhVienId());
        int _tinChiDatDuoc = sinhVienService.getSoTinChiSinhVienDatDuoc(_currentAccount.getSinhVien().getSinhVienId());

        TienDoHocTap _tienDoHocTap = TienDoHocTap.builder()
                .tongTinChi(_tongTinChi)
                .tinChiDatDuoc(_tinChiDatDuoc)
                .build();

        return TienDoHocTapResponse.builder()
                .status(ResponseStatus.OK)
                .message("L???y th??ng tin ti???n ????? h???c t???p th??nh c??ng.")
                .data(_tienDoHocTap)
                .build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public DiemThiResponse getDiemThi() {
        Account _currentAccount = accountService.getCurrentAccount();
        DateFormat _df = new SimpleDateFormat("YYYY");

        try {
            List<HocKy> _hocKys = hocKyService.findHocKyBySinhVienId(_currentAccount.getSinhVien().getSinhVienId());
            List<DiemHocKy> _diemHocKys = new ArrayList<>();


            _hocKys.forEach(i -> {
                List<SinhVienLopHocPhan> _sinhVienLopHocPhans = sinhVienLopHocPhanService.getSinhVienLopHocPhanByHocKy(_currentAccount.getSinhVien().getSinhVienId(), i.getHocKyId());

                List<DiemMonHoc> _diemMonHocs = new ArrayList<>();

                _sinhVienLopHocPhans.forEach(_i -> {


                    if (_i.getDiemCuoiKy() != null) {
                        Double _diemTrungBinh = Helper.round(Helper.tinhDiemTrungBinhh(_i), 2);

                        DiemMonHoc _diemMonHoc = DiemMonHoc.builder()
                                .tenMonHoc(_i.getLopHocPhan().getHocPhan().getMonHoc().getTenMonHoc())
                                .diemCuoiKy(_i.getDiemCuoiKy())
                                .diemGiuaKy(_i.getDiemGiuaKy())
                                .diemThuongKy(_i.getDiemThuongKy())
                                .diemThucHanh(_i.getDiemThucHanh())
                                .diemTrungBinh(_diemTrungBinh)
                                .ghiChu(_i.getGhiChu())
                                .build();

                        _diemMonHocs.add(_diemMonHoc);
                    }else {
                        DiemMonHoc _diemMonHoc = DiemMonHoc.builder()
                                .tenMonHoc(_i.getLopHocPhan().getHocPhan().getMonHoc().getTenMonHoc())
                                .diemCuoiKy(_i.getDiemCuoiKy())
                                .diemGiuaKy(_i.getDiemGiuaKy())
                                .diemThuongKy(_i.getDiemThuongKy())
                                .diemThucHanh(_i.getDiemThucHanh())
                                .ghiChu(_i.getGhiChu())
                                .build();

                        _diemMonHocs.add(_diemMonHoc);
                    }


                });

                DiemHocKy _diemHocKy = DiemHocKy.builder()
                        .tenHocKy("H???c k??? " + i.getSoThuTu() + "(" + _df.format(i.getNamHoc().getStartDate()) + "-" + _df.format(i.getNamHoc().getEndDate()) + ")")
                        .monHocs(_diemMonHocs)
                        .build();

                _diemHocKys.add(_diemHocKy);
            });

            return DiemThiResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin m??n h???c th??nh c??ng!")
                    .data(_diemHocKys)
                    .build();
        } catch (ParseException e) {
            return DiemThiResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("L???y th??ng tin ??i???m thi kh??ng th??nh c??ng.")
                    .errors(new ArrayList<>() {{
                        add(new ErrorsResponse("L???i h??? th???ng!"));
                    }})
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    public LichHocResponse getLichHoc(String date) {
        DateFormat formatter = new SimpleDateFormat("EEEE", new Locale("vi", "VN"));
        Helper.getInstance();

        try {
            DateTime _dateTime = new DateTime(date);

            Date _date = _dateTime.toDate();
            List<Date> _dateOfWeek = Helper.getDatesInWeek(_date, 2);
            List<DayOfWeek> _listDateOfWeek = new ArrayList<>();

            for (Date _i : _dateOfWeek) {
                _listDateOfWeek.add(
                        DayOfWeek.builder()
                                .name(formatter.format(_i)).build()
                );
            }

            Account account = accountService.getCurrentAccount();

            List<LichHoc> _lichHocs = lichHocService.getLichHocBySinhVienId(account.getSinhVien().getSinhVienId(), new java.sql.Date(_dateOfWeek.get(0).getTime()), new java.sql.Date(_dateOfWeek.get(_dateOfWeek.size() - 1).getTime()));

            System.out.println(_lichHocs);

            _lichHocs.forEach(i -> {
                int _ngayHocTrongTuan = i.getNgayHocTrongTuan();

                DayOfWeek _ds = _listDateOfWeek.get(_ngayHocTrongTuan);

                List<LichHocRes> _lichHocRes;

                if (_ds.getMonHocs() == null) {
                    _lichHocRes = new ArrayList<>();
                } else {
                    _lichHocRes = _ds.getMonHocs();
                }

                _lichHocRes.add(LichHocRes.builder()
                        .ghiChu(i.getGhiChu())
                        .giangVien(i.getLopHocPhan().getGiangViens().iterator().next().getHoTenDem() + " " + i.getLopHocPhan().getGiangViens().iterator().next().getTen())
                        .lopHocPhan(i.getLopHocPhan().getMaLopHocPhan())
                        .tenMonHoc(i.getLopHocPhan().getHocPhan().getMonHoc().getTenMonHoc())
                        .tiet(Integer.toString(i.getTietHocBatDau()) + " - " + Integer.toString(i.getTietHocKetThuc()))
                        .phong(i.getPhongHoc().getTenPhongHoc())
                        .nhomThucHanh(i.getNhomThucHanh())
                        .tenLopHocPhan(i.getLopHocPhan().getTenLopHocPhan())
                        .build());

                _listDateOfWeek.set(_ngayHocTrongTuan, DayOfWeek.builder()
                        .name(_ds.getName())
                        .monHocs(_lichHocRes)
                        .build());
            });

            return LichHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("L???y th??ng tin l???ch h???c th??nh c??ng.")
                    .data(_listDateOfWeek).build();
        } catch (Exception e) {
            e.printStackTrace();
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("L???y th??ng tin l???ch h???c kh??ng th??nh c??ng!")
                    .errors(new ArrayList<>() {{
                        add(new ErrorsResponse("Ng??y chuy???n v?? kh??ng h???p l???!"));
                    }})
                    .build();
        }


    }

    @PreAuthorize("isAuthenticated()")
    public SinhViensResponse getSinhViens() {
        List<SinhVien> sinhViens = sinhVienService.findAllSinhVien();
        return SinhViensResponse.builder()
                .status(ResponseStatus.OK)
                .data(sinhViens)
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    public SinhVienResponse getSinhVienById(int sinhVienId) {
        SinhVien sinhVien = sinhVienService.findSinhVienById(sinhVienId);
        if (sinhVien != null) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhVien)
                    .build();
        }
        return SinhVienResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y Sinh vi??n"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoasResponse getKhoas() {
        List<KhoaVien> khoas = khoaRepository.findAll();
        return KhoasResponse.builder()
                .status(ResponseStatus.OK)
                .data(khoas)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse getKhoaById(int khoaVienId) {
        KhoaVien khoa = khoaService.findKhoaById(khoaVienId);
        if (khoa != null) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(khoa)
                    .build();
        }
        return KhoaResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y Khoa"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhsResponse getChuyenNganhs() {
        List<ChuyenNganh> chuyenNganhs = chuyenNganhRespository.findAll();
        return ChuyenNganhsResponse.builder()
                .status(ResponseStatus.OK)
                .data(chuyenNganhs)
                .build();
    }

    @Autowired
    public ChuyenNganhService chuyenNganhService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse getChuyenNganhById(int chuyenNganhId) {
        ChuyenNganh chuyenNganh = chuyenNganhService.findChuyenNganhById(chuyenNganhId);
        if (chuyenNganh != null) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(chuyenNganh)
                    .build();
        }
        return ChuyenNganhResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y Chuy??n ng??nh"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhViensResponse getSinhVienWithKhoaVienId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException {
        List<SinhVien> sinhViens = sinhVienService.findSinhVienByKhoaVienId(khoaVienId);
        if (sinhViens.size() > 0) {
            return SinhViensResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhViens)
                    .build();
        }
        return SinhViensResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch Sinh vi??n"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhsResponse getChuyenNganhWithKhoaVienId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException {
        List<ChuyenNganh> chuyenNganhs = chuyenNganhService.getChuyenNganhByKhoaVienId(khoaVienId);
        if (chuyenNganhs.size() > 0) {
            return ChuyenNganhsResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(chuyenNganhs)
                    .build();
        }
        return ChuyenNganhsResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch Chuy??nh ng??nh"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhViensResponse getSinhVienWithKhoaVienIdAndNgayVaoTruong(int khoaVienId, String ngayVaoTruong) throws NoSuchFieldException, IllegalAccessException {
        List<SinhVien> sinhViens = sinhVienService.findSinhVienByKhoaVienIdAndNgayVaoTruong(khoaVienId, ngayVaoTruong);
        if (sinhViens.size() > 0) {
            return SinhViensResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhViens)
                    .build();
        }
        return SinhViensResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch Sinh vi??n"));
                    }
                })
                .build();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocsResponse getNamHocWithKhoaVienId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException, ParseException {
        List<NamHoc> namHocs = sinhVienService.findNamHocByKhoaVienId(khoaVienId);
        if (namHocs.size() > 0) {
            return NamHocsResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(namHocs)
                    .build();
        }
        return NamHocsResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch n??m h???c"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocsResponse getMonHocWithName(String tenMonHoc) throws NoSuchFieldException, IllegalAccessException {
        List<MonHoc> monHocs = monHocService.getMonHocWithName(tenMonHoc);
        if (monHocs.size() > 0) {
            return MonHocsResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(monHocs)
                    .build();
        }
        return MonHocsResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch M??n H???c"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocsResponse getMonHocWithChuyenNganhId(int chuyenNganhId) throws NoSuchFieldException, IllegalAccessException {
        List<MonHoc> monHocs = monHocService.getMonHocWithChuyenNganh(chuyenNganhId);
        if (monHocs.size() > 0) {
            return MonHocsResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(monHocs)
                    .build();
        }
        return MonHocsResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch M??n H???c"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhansResponse getLopHocPhanWithChuyenNganhId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException {
        List<LopHocPhan> lopHocPhans = lopHocPhanService.getLopHocPhanByKhoaVienId(khoaVienId);
        if (lopHocPhans.size() > 0) {
            return LopHocPhansResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(lopHocPhans)
                    .build();
        }
        return LopHocPhansResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch L???p h???c ph???n"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhansResponse getHocPhanWithChuyenNganhId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException {
        List<HocPhan> hocPhans = hocPhanService.getHocPhanByKhoaVienId(khoaVienId);
        if (hocPhans.size() > 0) {
            return HocPhansResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocPhans)
                    .build();
        }
        return HocPhansResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y danh s??ch H???c ph???n"));
                    }
                })
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    public LopsResponse getLops() {
        List<Lop> lops = lopRepository.findAll();
        return LopsResponse.builder()
                .status(ResponseStatus.OK)
                .data(lops).build();
    }

    @PreAuthorize("isAuthenticated()")
    public SinhVienResponse getProfile() {
        Account account = accountService.getCurrentAccount();
        if (account == null) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("L???y th??ng tin sinh vi??n kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("X???y ra l???i trong qu?? tr??nh get user"));
                        }
                    })
                    .build();
        }

        return SinhVienResponse.builder()
                .status(ResponseStatus.OK)
                .message("L???y th??ng tin sinh vi??n th??nh c??ng!")
                .data(account.getSinhVien())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKysResponse getHocKys() {
        List<HocKy> hockys = hocKyRepository.findAll();
        return HocKysResponse.builder()
                .status(ResponseStatus.OK)
                .data(hockys)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse getHocKyById(int hocKyId) {
        HocKy hocKy = hocKyRepository.findById(hocKyId).get();
        if (hocKy != null) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocKy)
                    .build();
        }
        return HocKyResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Kh??ng t??m th???y H???c K???")
                .data(null)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocsResponse getMonHocs() {
        List<MonHoc> monHocs = monHocRepository.findAll();
        return MonHocsResponse.builder()
                .status(ResponseStatus.OK)
                .data(monHocs)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocResponse getMonHocById(int monHocId) {
        MonHoc monHoc = monHocRepository.findById(monHocId).get();
        if (monHoc != null) {
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(monHoc)
                    .build();
        }
        return MonHocResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("kh??ng t??m th???y M??n H???c")
                .data(null)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhansResponse getLopHocPhans() {
        List<LopHocPhan> lopHocPhans = lopHocPhanRepository.findAll();
        return LopHocPhansResponse.builder()
                .status(ResponseStatus.OK)
                .data(lopHocPhans)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhanRespone getLopHocPhanById(int lopHocPhanId) {
        LopHocPhan lopHocPhan = lopHocPhanService.findLopHocPhanById(lopHocPhanId);
        if (lopHocPhan != null) {
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.OK)
                    .data(lopHocPhan)
                    .build();
        }
        return LopHocPhanRespone.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y l???p h???c ph???n"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhansResponse getHocPhans() {
        List<HocPhan> hocPhans = hocPhanRepository.findAll();
        return HocPhansResponse.builder()
                .status(ResponseStatus.OK)
                .data(hocPhans)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse getHocPhanById(int hocPhanId) {
        HocPhan hocPhan = hocPhanService.findHocPhanById(hocPhanId);
        if (hocPhan != null) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocPhan)
                    .build();
        }
        return HocPhanResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y h???c ph???n"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocsResponse getNamHocs() {
        List<NamHoc> namHocs = namHocRepository.findAll();
        return NamHocsResponse.builder()
                .status(ResponseStatus.OK)
                .data(namHocs)
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocResponse getNamHocById(int namHocId) {
        NamHoc namHoc = namHocService.findNamHocById(namHocId);
        if (namHoc != null) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(namHoc)
                    .build();
        }
        return NamHocResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("T??m kh??ng th??nh c??ng")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y  n??m h???c"));
                    }
                })
                .build();
    }
}
