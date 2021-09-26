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

    @PreAuthorize("isAuthenticated()")
    public SinhVienResponse createSinhVien(SinhVienInput inputs){
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien(inputs.getMaSinhVien());
        sinhVien.setMaHoSo(inputs.getMaHoSo());
        sinhVien.setHoTenDem(inputs.getHoTenDem());
        sinhVien.setTen(inputs.getTen());
//        sinhVien.setImage(inputs.getImage());
        sinhVien.setGioiTinh(inputs.isGioiTinh());
        sinhVien.setNgaySinh(inputs.getNgaySinh());
        sinhVien.setBacDaoTao(inputs.getBacDaoTao());
        sinhVien.setTrangThai(inputs.getTrangThai());
        sinhVien.setLoaiHinhDaoTao(inputs.getLoaiHinhDaoTao());
        sinhVien.setNgayVaoTruong(inputs.getNgayVaoTruong());
        sinhVien.setNgayVaoDoan(inputs.getNgayVaoDoan());
        sinhVien.setNgayVaoDang(inputs.getNgayVaoDang());
        sinhVien.setSoDienThoai(inputs.getSoDienThoai());
        sinhVien.setDiaChi(inputs.getDiaChi());
        sinhVien.setDanToc(inputs.getDanToc());
        sinhVien.setEmail(inputs.getEmail());
        sinhVien.setTonGiao(inputs.getTonGiao());
        sinhVien.setHoKhauThuongTru(inputs.getHoKhauThuongTru());
        sinhVien.setNoiSinh(inputs.getNoiSinh());
        SinhVien sinhVienResp = sinhVienService.saveSinhVien(sinhVien);

        SinhVienResponse sinhVienResponse = new SinhVienResponse();
        if (sinhVienResp == null){
            sinhVienResponse.setMessage("Tao sinh vien khong thanh cong");
            sinhVienResponse.setStatus(ResponseStatus.ERROR);
        }else {
            sinhVienResponse.setMessage("Tao sinh vien thanh cong");
            sinhVienResponse.setStatus(ResponseStatus.OK);
            sinhVienResponse.setData(sinhVienResp);
        }
        return sinhVienResponse;
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
        if(isDeleted) {
            DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
            deleteUserResponse.setStatus(ResponseStatus.OK);
            deleteUserResponse.setMessage("Xoa user thanh cong");
            return deleteUserResponse;
        }else {
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

    @PreAuthorize("isAnonymous()")
    public RegisterResponse register(RegisterAccountInput inputs) {
        try {
            List<Image> images = new ArrayList<>();

            if (inputs.getUser().getImages() != null) {
                for (String name : inputs.getUser().getImages()) {
                    images.add(new Image(name));
                }
            }
            accountService.createAccount(
                    User.builder()
                            .name(inputs.getUser().getName())
                            .email(inputs.getUser().getEmail())
                            .images(images)
                            .build(), AccountInput
                            .builder()
                            .userName(inputs.getUsername())
                            .password(inputs.getPassword())
                            .build()
            );
            return RegisterResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo tài khỏan thành công.")
                    .build();
        } catch (UserAlreadyExistsException ex) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo tài khỏan không thành công.")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(ErrorsResponse.builder().message("Tài khỏan đã tồn tại!").build());
                        }
                    })
                    .build();
        }
    }
}
