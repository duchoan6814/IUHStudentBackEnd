package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.repository.KhoaRepository;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.repository.SinhVienRepository;
import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.khoa.KhoasResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhVienResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhViensResponse;
import com.iuh.IUHStudent.service.AccountService;
import com.iuh.IUHStudent.service.SinhVienService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private SinhVienRepository sinhVienRepository;

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
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).get();
        if (sinhVien != null) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhVien)
                    .build();
        }
        return SinhVienResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Khong tim thay sinh vien")
                .data(null)
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
        KhoaVien khoa = khoaRepository.findById(khoaVienId).get();
        if (khoa != null) {
            return KhoaResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(khoa)
                    .build();
        }
        return KhoaResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Khong tim thay khoa")
                .data(null)
                .build();
    }


    @PreAuthorize("isAuthenticated()")
    public LopsResponse getLops() {
        List<Lop> lops = lopRepository.findAll();
        return LopsResponse.builder()
                .status(ResponseStatus.OK)
                .data(lops).build();
    }

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

    @PreAuthorize("isAuthenticated()")
    public SinhVienResponse getProfile() {
        Account account = accountService.getCurrentAccount();
        if (account == null) {
            return SinhVienResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Lay thong tin sinh vien khong thanh cong!")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Xảy ra lỗi trong quá trình get user"));
                        }
                    })
                    .build();
        }

        return SinhVienResponse.builder()
                .status(ResponseStatus.OK)
                .message("Lay thong tin sinh vien thanh cong!")
                .data(account.getSinhVien())
                .build();
    }
}
