package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.*;
import com.iuh.IUHStudent.repository.ChuyenNganhRespository;
import com.iuh.IUHStudent.repository.KhoaRepository;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.repository.SinhVienRepository;

import com.iuh.IUHStudent.repository.*;

import com.iuh.IUHStudent.response.*;
import com.iuh.IUHStudent.response.khoa.KhoaResponse;
import com.iuh.IUHStudent.response.khoa.KhoasResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhVienResponse;
import com.iuh.IUHStudent.response.sinhvien.SinhViensResponse;
import com.iuh.IUHStudent.service.AccountService;
import com.iuh.IUHStudent.service.ChuyenNganhService;
import com.iuh.IUHStudent.service.KhoaService;
import com.iuh.IUHStudent.service.SinhVienService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.text.ParseException;
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
    public KhoaService khoaService;

    @Autowired
    private HocKyRepository hocKyRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private ChuyenNganhRespository chuyenNganhRespository;

    @Autowired
    private MonHocRepository monHocRepository;


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
                .errors(new ArrayList<>(){
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
                .message("Xoa khong thanh công")
                .errors(new ArrayList<>(){
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
                .errors(new ArrayList<>(){
                    {
                        add(new ErrorsResponse("Không tìm thấy Chuyên ngành"));
                    }
                })
                .build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhViensResponse getSinhVienWithKhoaVienId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException {
        List<SinhVien> sinhViens = sinhVienService.finSinhVienByKhoaVienId(khoaVienId);
        if (sinhViens.size() > 0) {
            return SinhViensResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhViens)
                    .build();
        }
        return SinhViensResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Tìm không thành công")
                .errors(new ArrayList<>(){
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Sinh viên thuộc khoa"));
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
                .errors(new ArrayList<>(){
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Chuyênh ngành thuộc khoa"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SinhViensResponse getSinhVienWithKhoaVienIdAndNgayVaoTruong(int khoaVienId, String ngayVaoTruong) throws NoSuchFieldException, IllegalAccessException {
        List<SinhVien> sinhViens = sinhVienService.finSinhVienByKhoaVienIdAndNgayVaoTruong(khoaVienId,ngayVaoTruong);
        if (sinhViens.size() > 0) {
            return SinhViensResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(sinhViens)
                    .build();
        }
        return SinhViensResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Tìm không thành công")
                .errors(new ArrayList<>(){
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách Sinh viên"));
                    }
                })
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NamHocsResponse getNamHocWithKhoaVienId(int khoaVienId) throws NoSuchFieldException, IllegalAccessException, ParseException {
        List<NamHoc> namHocs = sinhVienService.finNamHocByKhoaVienId(khoaVienId);
        if (namHocs.size() > 0) {
            return NamHocsResponse.builder()
                    .status(ResponseStatus.OK)
                    .data(namHocs)
                    .build();
        }
        return NamHocsResponse.builder()
                .status(ResponseStatus.ERROR)
                .message("Tìm không thành công")
                .errors(new ArrayList<>(){
                    {
                        add(new ErrorsResponse("Không tìm thấy danh sách năm học"));
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
}
