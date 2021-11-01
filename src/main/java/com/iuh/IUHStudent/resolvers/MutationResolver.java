package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.entityinput.KhoaInput;
import com.iuh.IUHStudent.entityinput.SinhVienUpdateInput;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.RegisterAccountInput;
import com.iuh.IUHStudent.entityinput.account_input.UpdatePasswordInput;
import com.iuh.IUHStudent.exception.*;
import com.iuh.IUHStudent.repository.KhoaRepository;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhVienResponse;
import com.iuh.IUHStudent.response.sinhvien.UpdateSVResponse;
import com.iuh.IUHStudent.service.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AccountService accountService;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private LopService lopService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private KhoaRepository khoaRepository;

    @Autowired
    private KhoaServiceImpl khoaService;

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
                    .status(ResponseStatus.OK)
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
                    .message("Xoa sinh vien thanh cong")
                    .build();
        }catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xoa khong thanh công")
                    .errors(new ArrayList<>(){
                        {
                            add(new ErrorsResponse("Khong tim thay sinh vien"));
                        }
                    })
                    .build();
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhVienResponse addSinhVienVaoLop(int sinhVienId, int lopId) {
        try{
           SinhVien sinhVien = lopService.addSinhVienVaoLop(sinhVienId,lopId);
            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Them sinh vien vao lop thanh cong")
                    .data(sinhVien)
                    .build();
        } catch (LopNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Them sinh vien vao lop khong thanh công")
                    .errors(new ArrayList<>(){
                        {
                            add(new ErrorsResponse("Khong tim thay lop"));
                        }
                    })
                    .build();
        }catch (UserNotFoundException e) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Them sinh vien vao lop khong thanh công")
                    .errors(new ArrayList<>() {
                        {
                            add(new ErrorsResponse("Khong tim thay sinh vien"));
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
                    .status(ResponseStatus.OK)
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
                    .data(sinhVien)
                    .message("update sinh viên thành công").build();
        }
        return UpdateSVResponse.builder()
                .errors(new ArrayList<>() {
                    {
                        add(new ErrorsResponse("Không tìm thấy sinh viên"));
                    }
                })
                .message("update sinh viên không thành công").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse updateKhoa(KhoaInput inputs,int khoaId) {
        KhoaVien khoa = khoaService.findKhoaById(khoaId);
            if(khoa != null) {
                khoa.setTenKhoaVien(inputs.getTenKhoaVien());
                khoa.setLienKet(inputs.getLienKet());
                khoaRepository.save(khoa);
                return KhoaResponse.builder()
                        .status(ResponseStatus.OK)
                        .data(khoa)
                        .message("update khoa thành công").build();
            }
                return KhoaResponse.builder()
                        .status(ResponseStatus.ERROR)
                        .errors(new ArrayList<>() {
                            {
                                add(new ErrorsResponse("Không tìm thấy khoa"));
                            }
                        })
                        .message("update khoa không thành công").build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public KhoaResponse deleteKhoa(int khoaId) {
        try {
            khoaService.deleteKhoa(khoaId);
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Xoa khoa thanh cong")
                    .build();
        }catch (KhoaNotFoundException e) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Xoa khong thanh công")
                    .errors(new ArrayList<>(){
                        {
                            add(new ErrorsResponse("Khong tim thay khoa"));
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
            lopResponse.setMessage("Tao lop khong thanh cong");
            lopResponse.setStatus(ResponseStatus.ERROR);

        } else {
            lopResponse.setMessage("Tao lop thanh cong");
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
            deleteUserResponse.setMessage("Xoa user thanh cong");
            return deleteUserResponse;
        } else {
            DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
            deleteUserResponse.setStatus(ResponseStatus.ERROR);
            deleteUserResponse.setMessage("Xoa user khong thanh cong");
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
                        add(ErrorsResponse.builder().message("Token khong dung!").build());
                    }})
                    .build();
        }
    }

}
