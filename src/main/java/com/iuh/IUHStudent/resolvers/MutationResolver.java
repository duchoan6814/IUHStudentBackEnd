package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.entityinput.ChuyenNganhInput;

import com.iuh.IUHStudent.entityinput.ChuyenNganhInput;

import com.iuh.IUHStudent.entityinput.HocKyInput;

import com.iuh.IUHStudent.entityinput.KhoaInput;
import com.iuh.IUHStudent.entityinput.MonHocInput;
import com.iuh.IUHStudent.entityinput.SinhVienUpdateInput;
import com.iuh.IUHStudent.entityinput.*;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.RegisterAccountInput;
import com.iuh.IUHStudent.entityinput.account_input.UpdatePasswordInput;
import com.iuh.IUHStudent.exception.*;
import com.iuh.IUHStudent.repository.*;
import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhVienResponse;
import com.iuh.IUHStudent.response.sinhvien.UpdateSVResponse;
import com.iuh.IUHStudent.service.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private LopService lopService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private KhoaRepository khoaRepository;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private ChuyenNganhRespository chuyenNganhRespository;

    @Autowired
    private ChuyenNganhRespository chuyenNganhResponse;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private HocKyRepository hocKyRepository;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private MonHocService monHocService;

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private NamHocRepository namHocRepository;

    @Autowired
    private NamHocService namHocService;


    @PreAuthorize("isAnonymous()")
    public AccountResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.OK)
                    .message("????ng nh???p th??nh c??ng.")
                    .data(accountService.getCurrentAccount())
                    .build();
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.ERROR)
                    .message("????ng nh???p kh??ng th??nh c??ng.")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("T??n t??i kho???n ho???c m???t kh???u kh??ng ????ng!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public RegisterResponse createSinhVien(RegisterAccountInput inputs) {

        SinhVien sinhVien = SinhVien.builder()
                .maSinhVien(inputs.getSinhVien().getMaSinhVien())
                .ngaySinh(inputs.getSinhVien().getNgaySinh())
                .maHoSo(inputs.getSinhVien().getMaHoSo())
                .hoTenDem(inputs.getSinhVien().getHoTenDem())
                .ten(inputs.getSinhVien().getTen())
                .gioiTinh(inputs.getSinhVien().isGioiTinh())
                .bacDaoTao(inputs.getSinhVien().getBacDaoTao())
                .trangThai(inputs.getSinhVien().getTrangThai())
                .loaiHinhDaoTao(inputs.getSinhVien().getLoaiHinhDaoTao())
                .ngayVaoTruong(inputs.getSinhVien().getNgayVaoTruong())
                .ngayVaoDoan(inputs.getSinhVien().getNgayVaoDoan())
                .soDienThoai(inputs.getSinhVien().getSoDienThoai())
                .diaChi(inputs.getSinhVien().getDiaChi())
                .noiSinh(inputs.getSinhVien().getNoiSinh())
                .hoKhauThuongTru(inputs.getSinhVien().getHoKhauThuongTru())
                .danToc(inputs.getSinhVien().getDanToc())
                .ngayVaoDang(inputs.getSinhVien().getNgayVaoDang())
                .email(inputs.getSinhVien().getEmail())
                .tonGiao(inputs.getSinhVien().getTonGiao())
                .build();

        AccountInput input = AccountInput.builder()
                .userName(inputs.getUsername())
                .password(inputs.getPassword())
                .build();

        try {
            Account account = accountService.createAccount(sinhVien, input);
            return RegisterResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o sinh vi??n th??nh c??ng")
                    .data(account.getSinhVien())
                    .build();
        } catch (UserAlreadyExistsException exception) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o sinh vi??n kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Sinh vi??n ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhVienResponse deleteSinhVien(int sinhVienId) {
        try {
            sinhVienService.deleteSinhVien(sinhVienId);
            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a sinh vi??n th??nh c??ng")
                    .build();
        } catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y sinh vi??n"));
                        }
                    })
                    .build();
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhVienResponse addSinhVienVaoLop(int sinhVienId, int lopId) {
        try {
            SinhVien sinhVien = lopService.addSinhVienVaoLop(sinhVienId, lopId);
            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Th??m sinh vi??n v??o l???p th??nh c??ng")
                    .data(sinhVien)
                    .build();
        } catch (LopNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m sinh vi??n v??o l???p kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y l???p"));
                        }
                    })
                    .build();
        } catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Th??m sinh vi??n v??o l???p kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y sinh vi??n"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse createKhoa(KhoaInput inputs) {
        KhoaVien khoa = KhoaVien.builder()
                .tenKhoaVien(inputs.getTenKhoaVien())
                .lienKet(inputs.getLienKet())
                .build();
        try {
            KhoaVien khoaResp = khoaRepository.save(khoa);
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o khoa th??nh c??ng")
                    .data(khoaResp)
                    .build();
        } catch (KhoaNotFoundException exception) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o khoa kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Khoa ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UpdateSVResponse updateSinhVien(SinhVienUpdateInput inputs, String maSinhVien) {
        SinhVien sinhVien = sinhVienService.findSinhVienByMa(maSinhVien);
        if (sinhVien != null) {
            sinhVien.setMaHoSo(inputs.getMaHoSo());
            sinhVien.setHoTenDem(inputs.getHoTenDem());
            sinhVien.setTen(inputs.getTen());
            sinhVien.setImage(inputs.getImage());
            sinhVien.setGioiTinh(inputs.isGioiTinh());
            sinhVien.setBacDaoTao(inputs.getBacDaoTao());
            sinhVien.setTrangThai(inputs.getTrangThai());
            sinhVien.setLoaiHinhDaoTao(inputs.getLoaiHinhDaoTao());
            sinhVien.setNgayVaoTruong(inputs.getNgayVaoTruong());
            sinhVien.setNgaySinh(inputs.getNgaySinh());
            sinhVien.setNgayVaoDoan(inputs.getNgayVaoDoan());
            sinhVien.setSoDienThoai(inputs.getSoDienThoai());
            sinhVien.setDiaChi(inputs.getDiaChi());
            sinhVien.setNoiSinh(inputs.getNoiSinh());
            sinhVien.setHoKhauThuongTru(inputs.getHoKhauThuongTru());
            sinhVien.setDanToc(inputs.getDanToc());
            sinhVien.setNgayVaoDang(inputs.getNgayVaoDang());
            sinhVien.setEmail(inputs.getEmail());
            sinhVien.setTonGiao(inputs.getTonGiao());
            sinhVienService.saveSinhVien(sinhVien);
            return UpdateSVResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhVien)
                    .message("C???p nh???t sinh vi??n th??nh c??ng").build();
        }
        return UpdateSVResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y sinh vi??n"));
                    }
                })
                .message("C???p nh???t sinh sinh vi??n kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse updateKhoa(KhoaInput inputs, int khoaId) {
        KhoaVien khoa = khoaService.findKhoaById(khoaId);
        if (khoa != null) {
            khoa.setTenKhoaVien(inputs.getTenKhoaVien());
            khoa.setLienKet(inputs.getLienKet());
            khoaRepository.save(khoa);
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(khoa)
                    .message("C???p nh???t khoa th??nh c??ng").build();
        }
        return KhoaResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y khoa"));
                    }
                })
                .message("C???p nh???t khoa kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse deleteKhoa(int khoaId) {
        try {
            khoaService.deleteKhoa(khoaId);
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a khoa th??nh c??ng")
                    .build();
        } catch (KhoaNotFoundException e) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y khoa"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhVienResponse deleteAllSinhVien() {
        sinhVienService.deleteAllSinhVien();
        return SinhVienResponse.builder()
                .status(ResponseStatus.OK)
                .message("X??a t???t c??? sinh vi??n th??nh c??ng")
                .build();

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse createChuyenNganh(ChuyenNganhInput inputs) {
        ChuyenNganh chuyenNganh = ChuyenNganh.builder()
                .tenChuyenNganh(inputs.getTenChuyenNganh())
                .build();
        try {
            ChuyenNganh chuyenNganhResp = chuyenNganhResponse.save(chuyenNganh);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o chuy??n ng??nh th??nh c??ng")
                    .data(chuyenNganhResp)
                    .build();
        } catch (ChuyenNganhNotFoundException exception) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o chuy??n ng??nh kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Chuy??n ng??nh ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse updateChuyenNganh(ChuyenNganhInput inputs, int chuyenNganhId) {
        ChuyenNganh chuyenNganh = chuyenNganhService.findChuyenNganhById(chuyenNganhId);
        if (chuyenNganh != null) {
            chuyenNganh.setTenChuyenNganh(inputs.getTenChuyenNganh());
            chuyenNganhResponse.save(chuyenNganh);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(chuyenNganh)
                    .message("C???p nh???t Chuy??n ng??nh th??nh c??ng").build();
        }
        return ChuyenNganhResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y chuy??n ng??nh"));
                    }
                })
                .message("C???p nh???t chuy??n ng??nh kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse deleteChuyenNganh(int chuyenNganhId) {
        try {
            chuyenNganhService.deleteChuyenNganh(chuyenNganhId);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a chuy??n ng??nh th??nh c??ng")
                    .build();
        } catch (ChuyenNganhNotFoundException e) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y chuy??n ng??nh"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhanRespone createLopHocPhan(LopHocPhanInput inputs) {
        LopHocPhan lopHocPhan = LopHocPhan.builder()
                .maLopHocPhan(inputs.getMaLopHocPhan())
                .tenLopHocPhan(inputs.getTenLopHocPhan())
                .tenVietTat(inputs.getTenVietTat())
                .soNhomThucHanh(inputs.getSoNhomThucHanh())
                .trangThaiLopHocPhan(inputs.getTrangThaiLopHocPhan())
                .soLuongToiDa(inputs.getSoLuongToiDa())
                .moTa(inputs.getMoTa())
                .build();
        try {
            LopHocPhan lopHocPhanResp = lopHocPhanRepository.save(lopHocPhan);
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o l???p h???c ph???n th??nh c??ng")
                    .data(lopHocPhanResp)
                    .build();
        } catch (LopHocPhanNotFoundException exception) {
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o l???p h???c ph???n kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("L???p h???c ph???n ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse addChuyenNganh(ChuyenNganhInputs inputs) {
        ChuyenNganh chuyenNganh = ChuyenNganh.builder()
                .tenChuyenNganh(inputs.getTenChuyenNganh())
                .khoaVien(khoaRepository.getById(inputs.getKhoaVienId()))
                .build();
        try {
            ChuyenNganh chuyenNganhResp = chuyenNganhResponse.save(chuyenNganh);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o chuy??n ng??nh th??nh c??ng")
                    .data(chuyenNganhResp)
                    .build();
        } catch (ChuyenNganhNotFoundException exception) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o chuy??n ng??nh kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Chuy??n ng??nh ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public LopResponse createLop(String tenLop, String khoaHoc) {
        Lop lop = new Lop();
        lop.setTenLop(tenLop);
        lop.setKhoaHoc(khoaHoc);

        Lop lopResp = lopRepository.save(lop);

        LopResponse lopResponse = new LopResponse();
        if (lopResp == null) {
            lopResponse.setMessage("T???o l???p kh??ng th??nh c??ng");
            lopResponse.setStatus(ResponseStatus.ERROR);

        } else {
            lopResponse.setMessage("T???o l???p kh??ng th??nh c??ng");
            lopResponse.setStatus(ResponseStatus.OK);
            lopResponse.setData(lopResp);
        }
        return lopResponse;
    }

    @PreAuthorize("isAuthenticated()")
    public DeleteUserResponse deleteAccount(long id) {
        boolean isDeleted = accountService.deleteUser(id);
        if (isDeleted) {
            DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
            deleteUserResponse.setStatus(ResponseStatus.OK);
            deleteUserResponse.setMessage("X??a user th??nh c??ng");
            return deleteUserResponse;
        } else {
            DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
            deleteUserResponse.setStatus(ResponseStatus.ERROR);
            deleteUserResponse.setMessage("X??a user kh??ng th??nh c??ng");
            return deleteUserResponse;
        }
    }

    @PreAuthorize("isAuthenticated()")
    public AccountResponse changePassword(UpdatePasswordInput inputs) {
        try {
            Account account = accountService.updatePassword(accountService.getCurrentAccount().getId(), inputs);
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("?????i m???t kh???u th??nh c??ng!")
                    .data(account)
                    .build();
        } catch (BadCredentialsException ex) {
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("?????i m???t kh???u kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {{
                        add(ErrorsResponse.builder().message("M???t kh???u kh??ng ????ng!").build());
                    }})
                    .build();
        } catch (BadTokenException bex) {
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("?????i m???t kh???u kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {{
                        add(ErrorsResponse.builder().message("Token kh??ng ????ng!").build());
                    }})
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse createHocKy(HocKyInput inputs) {
        NamHoc _namHoc = namHocService.findNamHocById(inputs.getMaNamHoc());

        HocKy _hocKy;

        if (_namHoc != null) {
            _hocKy = HocKy.builder()
                    .soThuTu(inputs.getSoThuTu())
                    .moTa(inputs.getMoTa())
                    .namHoc(_namHoc)
                    .build();
        } else {
            _hocKy = HocKy.builder()
                    .soThuTu(inputs.getSoThuTu())
                    .moTa(inputs.getMoTa())
                    .build();
        }

        try {
            HocKy hocKyResp = hocKyRepository.save(_hocKy);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o H???c K??? th??nh c??ng")
                    .data(hocKyResp)
                    .build();
        } catch (HocKyNotFoundException exception) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o H???c K??? kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("H???c K??? ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    public LopHocPhanRespone updateLopHocPhan(LopHocPhanInput inputs, int lopHocPhanId) {
        LopHocPhan lopHocPhan = lopHocPhanService.findLopHocPhanById(lopHocPhanId);
        if (lopHocPhan != null) {
            lopHocPhan.setTenLopHocPhan(inputs.getTenLopHocPhan());
            lopHocPhan.setMaLopHocPhan(inputs.getMaLopHocPhan());
            lopHocPhan.setTenVietTat(inputs.getTenVietTat());
            lopHocPhan.setSoNhomThucHanh(inputs.getSoNhomThucHanh());
            lopHocPhan.setTrangThaiLopHocPhan(inputs.getTrangThaiLopHocPhan());
            lopHocPhan.setSoLuongToiDa(inputs.getSoLuongToiDa());
            lopHocPhan.setMoTa(inputs.getMoTa());
            lopHocPhanRepository.save(lopHocPhan);
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.OK)
                    .data(lopHocPhan)
                    .message("update l???p h???c ph???n  th??nh c??ng").build();
        }
        return LopHocPhanRespone.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y l???p h???c ph???n"));
                    }
                })
                .message("update l???p h???c ph???n kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhanRespone deleteLopHocPhan(int lopHocPhanId) {
        try {
            lopHocPhanService.deleteLopHocPhan(lopHocPhanId);
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Xo?? l???p h???c ph???n th??nh c??ng")
                    .build();
        } catch (LopHocPhanNotFoundException e) {
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xo?? kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y l???p h???c ph???n"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse createHocPhan(HocPhanInput inputs) {
        HocPhan hocPhan = HocPhan.builder()
                .maHocPhan(inputs.getMaHocPhan())
                .moTa(inputs.getMoTa())
                .batBuoc(inputs.isBatBuoc())
                .build();
        try {
            HocPhan hocPhanResp = hocPhanRepository.save(hocPhan);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o h???c ph???n th??nh c??ng")
                    .data(hocPhanResp)
                    .build();
        } catch (HocPhanNotFoundException exception) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("T???o h???c ph???n kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("H???c ph???n ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse updateHocPhan(HocPhanInput inputs, int hocPhanId) {
        HocPhan hocPhan = hocPhanService.findHocPhanById(hocPhanId);
        if (hocPhan != null) {
            hocPhan.setMaHocPhan(inputs.getMaHocPhan());
            hocPhan.setMoTa(inputs.getMoTa());
            hocPhan.setBatBuoc(inputs.isBatBuoc());
            hocPhanRepository.save(hocPhan);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocPhan)
                    .message("update h???c ph???n  th??nh c??ng").build();
        }
        return HocPhanResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y h???c ph???n"));
                    }
                })
                .message("update h???c ph???n kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse deleteHocPhan(int hocPhanId) {
        try {
            hocPhanService.deleteHocPhan(hocPhanId);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xo?? h???c ph???n th??nh c??ng")
                    .build();
        } catch (HocPhanNotFoundException e) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xo?? kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y  h???c ph???n"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse updateHocKy(HocKyInput inputs, int hocKyId) {
        HocKy hocKy = hocKyService.findHcoKyById(hocKyId);

        NamHoc _namHoc = namHocService.findNamHocById (inputs.getMaNamHoc());

        if (hocKy != null) {

            if(_namHoc != null) {
                hocKy.setSoThuTu(inputs.getSoThuTu());
                hocKy.setMoTa(inputs.getMoTa());
                hocKy.setNamHoc(_namHoc);

                HocKy hocKyRes = hocKyRepository.save(hocKy);
                return HocKyResponse.builder()
                        .status(ResponseStatus.OK)
                        .data(hocKyRes)
                        .message("C???p nh???t H???c K??? th??nh c??ng").build();
            }

            hocKy.setSoThuTu(inputs.getSoThuTu());
            hocKy.setMoTa(inputs.getMoTa());
            HocKy _hocKyRes = hocKyRepository.save(hocKy);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(_hocKyRes)
                    .message("C???p nh???t H???c K??? th??nh c??ng").build();
        }
        return HocKyResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y H???c K???"));
                    }
                })
                .message("C???p nh???t H???c K??? kh??ng th??nh c??ng").build();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse deleteHocKy(int hocKyId) {
        try {
            hocKyService.deleteHocKy(hocKyId);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("X??a h???c k??? th??nh c??ng")
                    .build();
        } catch (HocKyNotFoundException e) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y h???c k???"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocResponse createMonHoc(MonHocInput inputs) {
        MonHoc monHoc = MonHoc.builder()
                .tenMonHoc(inputs.getTenMonHoc())
                .moTa(inputs.getMoTa())
                .soTinChiLyThuyet(inputs.getSoTinChiLyThuyet())
                .soTinChiThucHanh(inputs.getSoTinChiThucHanh())
                .build();
        try {
            MonHoc monHocReps = monHocRepository.save(monHoc);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o M??n H???c th??nh c??ng")
                    .data(monHocReps)
                    .build();
        } catch (MonHocNotFoundException exception) {
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o M??n H???c kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("M??n H???c ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocResponse updateMonHoc(MonHocInput inputs, int monHocId) {
        MonHoc monHoc = monHocService.findMonHocById(monHocId);
        if (monHoc != null) {
            monHoc.setTenMonHoc(inputs.getTenMonHoc());
            monHoc.setMoTa(inputs.getMoTa());
            monHoc.setSoTinChiLyThuyet(inputs.getSoTinChiLyThuyet());
            monHoc.setSoTinChiThucHanh(inputs.getSoTinChiThucHanh());

            monHocRepository.save(monHoc);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(monHoc)
                    .message("C???p nh???t M??n H???c th??nh c??ng").build();
        }
        return MonHocResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y M??n H???c"));
                    }
                })
                .message("C???p nh???t M??n H???c kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocResponse deleteMonHoc(int monHocId) {
        try {
            monHocService.deleteMonHoc(monHocId);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xoa M??n H???c th??nh c??ng")
                    .build();
        } catch (MonHocNotFoundException e) {
            return MonHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y M??n H???c"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocResponse createNamHoc(NamHocInput inputs) {
        NamHoc namHoc = NamHoc.builder()
                .startDate(inputs.getStartDate())
                .endDate(inputs.getEndDate())
                .build();
        try {
            NamHoc namHocReps = namHocRepository.save(namHoc);
            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o N??m h???c th??nh c??ng")
                    .data(namHocReps)
                    .build();
        } catch (NamHocNotFoundException exception) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("T???o N??m h???c kh??ng th??nh c??ng!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("N??m h???c ???? t???n t???i!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocResponse updateNamHoc(NamHocInput inputs, int namHocId) {
        NamHoc namHoc = namHocService.findNamHocById(namHocId);
        if (namHoc != null) {
            namHoc.setStartDate(inputs.getStartDate());
            namHoc.setEndDate(inputs.getEndDate());

            namHocRepository.save(namHoc);
            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(namHoc)
                    .message("C???p nh???t N??m h???c th??nh c??ng").build();
        }
        return NamHocResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Kh??ng t??m th???y N??m H???c"));
                    }
                })
                .message("C???p nh???t N??m H???c kh??ng th??nh c??ng").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocResponse deleteNamHoc(int namHocId) {
        try {
            namHocService.deleteNamHoc(namHocId);
            return NamHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xoa N??m H???c th??nh c??ng")
                    .build();
        } catch (NamHocNotFoundException e) {
            return NamHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("X??a kh??ng th??nh c??ng")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Kh??ng t??m th???y N??m H???c"));
                        }
                    })
                    .build();
        }
    }
}
