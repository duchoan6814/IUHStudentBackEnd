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
import com.iuh.IUHStudent.response.ketQuaHocTap.KetQuaHocTap;
import com.iuh.IUHStudent.response.ketQuaHocTap.KetQuaHocTapResponse;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.khoa.KhoasResponse;
import com.iuh.IUHStudent.response.lichHoc.DayOfWeek;
import com.iuh.IUHStudent.response.lichHoc.LichHocRes;
import com.iuh.IUHStudent.response.lichHoc.LichHocResponse;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    NamHocService namHocService;

    @PreAuthorize("hasAnyAuthority('USER')")
    public KetQuaHocTapResponse getKetQuaHocTap(int hocKyId) {
        Account currentAccount = accountService.getCurrentAccount();

        List<SinhVienLopHocPhan> _sinhVienLopHocPhans = sinhVienLopHocPhanService.getSinhVienLopHocPhanByHocKy(currentAccount.getSinhVien().getSinhVienId(), hocKyId);

        List<KetQuaHocTap> _ketQuaHocTaps = new ArrayList<>();

        for (SinhVienLopHocPhan sinhVienLopHocPhan : _sinhVienLopHocPhans) {
            LopHocPhan _lopHocPhan = lopHocPhanService.findLopHocPhanById(sinhVienLopHocPhan.getLopHocPhan().getLopHocPhanId());

            List<SinhVienLopHocPhan> _listSinhVienLopHocPhan = sinhVienLopHocPhanService.getListSinhVienLopHocPhanByHocPhan(_lopHocPhan.getLopHocPhanId());


            double _diemTrungBinh = _listSinhVienLopHocPhan.stream().mapToDouble(a -> Helper.tinhDiemTrungBinhh(a)).average().getAsDouble();

            KetQuaHocTap _ketQuaHocTap = KetQuaHocTap.builder()
                    .monHoc(sinhVienLopHocPhan.getLopHocPhan().getHocPhan().getMonHoc())
                    .diem(Helper.tinhDiemTrungBinhh(sinhVienLopHocPhan))
                    .diemTrungBinh(_diemTrungBinh)
                    .build();

            _ketQuaHocTaps.add(_ketQuaHocTap);
        }

        return KetQuaHocTapResponse.builder()
                .status(ResponseStatus.OK)
                .message("Lấy thông tin kết quả học tập thành công.")
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
                .message("Lấy thông tin tiến độ học tập thành công.")
                .data(_tienDoHocTap)
                .build();
    }

    @PreAuthorize("hasAuthority('USER')")
    public DiemThiResponse getDiemThi() {
        Account _currentAccount = accountService.getCurrentAccount();
        DateFormat _df = new SimpleDateFormat("YYYY");

        try {
            List<HocKy> _hocKys = hocKyService.findHocKyBySinhVienId(93);
            List<DiemHocKy> _diemHocKys = new ArrayList<>();

            _hocKys.forEach(i -> {
                List<SinhVienLopHocPhan> _sinhVienLopHocPhans = sinhVienLopHocPhanService.getSinhVienLopHocPhanByHocKy(_currentAccount.getSinhVien().getSinhVienId(), i.getHocKyId());

                List<DiemMonHoc> _diemMonHocs = new ArrayList<>();

                _sinhVienLopHocPhans.forEach(_i -> {


                    Double _diemTrungBinh = Helper.round(Helper.tinhDiemTrungBinhh(_i), 2);

                    DiemMonHoc _diemMonHoc = DiemMonHoc.builder()
                            .tenMonHoc(_i.getLopHocPhan().getHocPhan().getMonHoc().getTenMonHoc())
                            .diemCuoiKy((float) _i.getDiemCuoiKy())
                            .diemGiuaKy((float) _i.getDiemGiuaKy())
                            .diemThuongKy(_i.getDiemThuongKy())
                            .diemThucHanh(_i.getDiemThucHanh())
                            .diemTrungBinh(_diemTrungBinh)
                            .ghiChu(_i.getGhiChu())
                            .build();

                    _diemMonHocs.add(_diemMonHoc);
                });

                DiemHocKy _diemHocKy = DiemHocKy.builder()
                        .tenHocKy("Học kỳ " + i.getSoThuTu() + "(" + _df.format(i.getNamHoc().getStartDate()) + "-" + _df.format(i.getNamHoc().getEndDate()) + ")")
                        .monHocs(_diemMonHocs)
                        .build();

                _diemHocKys.add(_diemHocKy);
            });

            return DiemThiResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Lấy thông tin môn học thành công!")
                    .data(_diemHocKys)
                    .build();
        } catch (ParseException e) {
            return DiemThiResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin điểm thi không thành công.")
                    .errors(new ArrayList<>() {{
                        add(new ErrorsResponse("Lỗi hệ thống!"));
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
                    .message("Lấy thông tin lịch học thành công.")
                    .data(_listDateOfWeek).build();
        } catch (Exception e) {
            System.out.println(e);
            return LichHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lấy thông tin lịch học không thành công!")
                    .errors(new ArrayList<>() {{
                        add(new ErrorsResponse("Ngày chuyền vô không hợp lệ!"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy Sinh viên"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy Khoa"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy Chuyên ngành"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Sinh viên"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Chuyênh ngành"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Sinh viên"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách năm học"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Môn Học"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Môn Học"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Lớp học phần"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Học phần"));
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
                    .message("Lấy thông tin sinh viên không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Xảy ra lỗi trong quá trình get user"));
                        }
                    })
                    .build();
        }

        return SinhVienResponse.builder()
                .status(ResponseStatus.OK)
                .message("Lấy thông tin sinh viên thành công!")
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
                .message("Không tìm thấy Học Kỳ")
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
                .message("không tìm thấy Môn Học")
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy lớp học phần"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy học phần"));
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
                .message("Tìm không thành công")
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy  năm học"));
                    }
                })
                .build();
    }
}
