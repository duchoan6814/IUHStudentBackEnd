package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.entityinput.SinhVienInput;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.RegisterAccountInput;
import com.iuh.IUHStudent.entityinput.account_input.UpdatePasswordInput;
import com.iuh.IUHStudent.exception.BadTokenException;
import com.iuh.IUHStudent.exception.UserAlreadyExistsException;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.repository.SinhVienRepository;
import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.service.AccountService;
import com.iuh.IUHStudent.service.SinhVienServiceImpl;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AccountService accountService;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private SinhVienServiceImpl sinhVienService;

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

    public SinhVienResponse deleteSinhVien(int sinhVienId) {
        SinhVien sinhVien = new SinhVien();
        sinhVienService.deleteSinhVien(sinhVienId);
        return SinhVienResponse.builder()
                .status(ResponseStatus.OK)
                .message("Xoa sinh vien thanh cong")
                .build();
    }

    @PreAuthorize("isAuthenticated()")
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
