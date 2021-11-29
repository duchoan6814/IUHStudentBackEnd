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
    private  HocPhanService hocPhanService;


    @PreAuthorize("isAnonymous()")
    public AccountResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.OK)
                    .message("Đăng nhập thành công.")
                    .data(accountService.getCurrentAccount())
                    .build();
        } catch (AuthenticationException ex) {
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.ERROR)
                    .message("Đăng nhập không thành công.")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Tên tài khoản hoặc mật khẩu không đúng!"));
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
                    .message("Tạo sinh viên thành công")
                    .data(account.getSinhVien())
                    .build();
        } catch (UserAlreadyExistsException exception) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo sinh viên không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Sinh viên đã tồn tại!"));
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
                    .message("Xóa sinh viên thành công")
                    .build();
        } catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy sinh viên"));
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
                    .message("Thêm sinh viên vào lớp thành công")
                    .data(sinhVien)
                    .build();
        } catch (LopNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm sinh viên vào lớp không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy lớp"));
                        }
                    })
                    .build();
        } catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Thêm sinh viên vào lớp không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy sinh viên"));
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
                    .message("Tạo khoa thành công")
                    .data(khoaResp)
                    .build();
        } catch (KhoaNotFoundException exception) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo khoa không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Khoa đã tồn tại!"));
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
                    .message("Cập nhật sinh viên thành công").build();
        }
        return UpdateSVResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy sinh viên"));
                    }
                })
                .message("Cập nhật sinh sinh viên không thành công").build();
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
                    .message("Cập nhật khoa thành công").build();
        }
        return KhoaResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy khoa"));
                    }
                })
                .message("Cập nhật khoa không thành công").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse deleteKhoa(int khoaId) {
        try {
            khoaService.deleteKhoa(khoaId);
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa khoa thành công")
                    .build();
        } catch (KhoaNotFoundException e) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy khoa"));
                        }
                    })
                    .build();
        }
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
                    .message("Tạo chuyên ngành thành công")
                    .data(chuyenNganhResp)
                    .build();
        } catch (ChuyenNganhNotFoundException exception) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo chuyên ngành không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Chuyên ngành đã tồn tại!"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse updateChuyenNganh(ChuyenNganhInput inputs , int chuyenNganhId) {
        ChuyenNganh chuyenNganh = chuyenNganhService.findChuyenNganhById(chuyenNganhId);
        if(chuyenNganh != null) {
            chuyenNganh.setTenChuyenNganh(inputs.getTenChuyenNganh());
            chuyenNganhResponse.save(chuyenNganh);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(chuyenNganh)
                    .message("Cập nhật Chuyên ngành thành công").build();
        }
        return ChuyenNganhResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy chuyên ngành"));
                    }
                })
                .message("Cập nhật chuyên ngành không thành công").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ChuyenNganhResponse deleteChuyenNganh(int chuyenNganhId) {
        try {
            chuyenNganhService.deleteChuyenNganh(chuyenNganhId);
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa chuyên ngành thành công")
                    .build();
        }catch (ChuyenNganhNotFoundException e) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa không thành công")
                    .errors(new ArrayList<>(){
                        {
                            add(new ErrorsResponse("Không tìm thấy chuyên ngành"));
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
                    .message("Tạo lớp học phần thành công")
                    .data(lopHocPhanResp)
                    .build();
        } catch (LopHocPhanNotFoundException exception) {
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo lớp học phần không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Lớp học phần đã tồn tại!"));
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
                    .message("Tạo chuyên ngành thành công")
                    .data(chuyenNganhResp)
                    .build();
        } catch (ChuyenNganhNotFoundException exception) {
            return ChuyenNganhResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo chuyên ngành không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Chuyên ngành đã tồn tại!"));
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
            lopResponse.setMessage("Tạo lớp không thành công");
            lopResponse.setStatus(ResponseStatus.ERROR);

        } else {
            lopResponse.setMessage("Tạo lớp không thành công");
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
            deleteUserResponse.setMessage("Xóa user thành công");
            return deleteUserResponse;
        } else {
            DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
            deleteUserResponse.setStatus(ResponseStatus.ERROR);
            deleteUserResponse.setMessage("Xóa user không thành công");
            return deleteUserResponse;
        }
    }

    @PreAuthorize("isAuthenticated()")
    public AccountResponse changePassword(UpdatePasswordInput inputs) {
        try {
            Account account = accountService.updatePassword(accountService.getCurrentAccount().getId(), inputs);
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đổi mật khẩu thành công!")
                    .data(account)
                    .build();
        } catch (BadCredentialsException ex) {
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đổi mật khẩu không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {{
                        add(ErrorsResponse.builder().message("Mật khẩu không đúng!").build());
                    }})
                    .build();
        } catch (BadTokenException bex) {
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đổi mật khẩu không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {{
                        add(ErrorsResponse.builder().message("Token không đúng!").build());
                    }})
                    .build();
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse createHocKy(HocKyInput inputs) {
        HocKy hocKy = HocKy.builder()
                .namBatDau(inputs.getNamBatDau())
                .namKetThuc(inputs.getNamKetThuc())
                .moTa(inputs.getMoTa())
                .build();
        try {
            HocKy hocKyResp = hocKyRepository.save(hocKy);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo Học Kỳ thành công")
                    .data(hocKyResp)
                    .build();
        } catch (HocKyNotFoundException exception) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo Học Kỳ không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Học Kỳ đã tồn tại!"));
                            }
                    })
                    .build();
        }
    }
    public LopHocPhanRespone updateLopHocPhan(LopHocPhanInput inputs , int lopHocPhanId) {
        LopHocPhan lopHocPhan = lopHocPhanService.findLopHocPhanById(lopHocPhanId);
        if(lopHocPhan != null) {
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
                    .message("update lớp học phần  thành công").build();
        }
        return LopHocPhanRespone.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy lớp học phần"));
                    }
                })
                .message("update lớp học phần không thành công").build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public LopHocPhanRespone deleteLopHocPhan(int lopHocPhanId) {
        try {
            lopHocPhanService.deleteLopHocPhan(lopHocPhanId);
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.OK)
                    .message("Xoá lớp học phần thành công")
                    .build();
        } catch (LopHocPhanNotFoundException e) {
            return LopHocPhanRespone.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xoá không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy lớp học phần"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse createHocPhan(HocPhanInput inputs) {
        HocPhan hocPhan = HocPhan.builder()
                .maHocPhan(inputs.getMaHocPhan())
                .soTinChiLyThuyet(inputs.getSoTinChiLyThuyet())
                .getSoTinChiThucHanh(inputs.getGetSoTinChiThucHanh())
                .moTa(inputs.getMoTa())
                .batBuoc(inputs.isBatBuoc())
                .build();
        try {
            HocPhan hocPhanResp = hocPhanRepository.save(hocPhan);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo học phần thành công")
                    .data(hocPhanResp)
                    .build();
        } catch (HocPhanNotFoundException exception) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo học phần không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Học phần đã tồn tại!"));
                        }
                    })
                    .build();
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse updateHocPhan(HocPhanInput inputs , int hocPhanId) {
        HocPhan hocPhan = hocPhanService.findHocPhanById(hocPhanId);
        if(hocPhan != null) {
            hocPhan.setMaHocPhan(inputs.getMaHocPhan());
            hocPhan.setSoTinChiLyThuyet(inputs.getSoTinChiLyThuyet());
            hocPhan.setGetSoTinChiThucHanh(inputs.getGetSoTinChiThucHanh());
            hocPhan.setMoTa(inputs.getMoTa());
            hocPhan.setBatBuoc(inputs.isBatBuoc());
            hocPhanRepository.save(hocPhan);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocPhan)
                    .message("update học phần  thành công").build();
        }
        return HocPhanResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy học phần"));
                    }
                })
                .message("update học phần không thành công").build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public HocPhanResponse deleteHocPhan(int hocPhanId) {
        try {
            hocPhanService.deleteHocPhan(hocPhanId);
            return HocPhanResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xoá học phần thành công")
                    .build();
        } catch (HocPhanNotFoundException e) {
            return HocPhanResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xoá không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy  học phần"));
                        }
                    })
                    .build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse updateHocKy(HocKyInput inputs, int hocKyId) {
        HocKy hocKy = hocKyService.findHcoKyById(hocKyId);
        if (hocKy != null) {
            hocKy.setNamBatDau(inputs.getNamBatDau());
            hocKy.setNamKetThuc(inputs.getNamKetThuc());
            hocKy.setMoTa(inputs.getMoTa());
            hocKyRepository.save(hocKy);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(hocKy)
                    .message("Cập nhật Học Kỳ thành công").build();
        }
        return HocKyResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy Học Kỳ"));
                    }
                })
                .message("Cập nhật Học Kỳ không thành công").build();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public HocKyResponse deleteHocKy(int hocKyId) {
        try {
            hocKyService.deleteHocKy(hocKyId);
            return HocKyResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa học kỳ thành công")
                    .build();
        } catch (HocKyNotFoundException e) {
            return HocKyResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy học kỳ"));
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
                .build();
        try {
            MonHoc monHocReps = monHocRepository.save(monHoc);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo Môn Học thành công")
                    .data(monHocReps)
                    .build();
        } catch (MonHocNotFoundException exception) {
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo Môn Học không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Mon Học đã tồn tại!"));
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
            monHocRepository.save(monHoc);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(monHoc)
                    .message("Cập nhật Môn Học thành công").build();
        }
        return MonHocResponse.builder()
                .status(ResponseStatus.ERROR)
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy Môn Học"));
                    }
                })
                .message("Cập nhật Môn Học không thành công").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MonHocResponse deleteMonHoc(int monHocId) {
        try {
            monHocService.deleteMonHoc(monHocId);
            return MonHocResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xóa môn học thành công")
                    .build();
        } catch (MonHocNotFoundException e) {
            return MonHocResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xóa không thành công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Không tìm thấy Môn Học"));
                        }
                    })
                    .build();
        }
    }
}
