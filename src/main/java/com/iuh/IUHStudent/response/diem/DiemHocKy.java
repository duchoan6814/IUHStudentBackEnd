package com.iuh.IUHStudent.response.diem;

import lombok.Builder;

import java.util.List;

@Builder
public class DiemHocKy {
    private String tenHocKy;
    private List<DiemMonHoc> monHocs;
}
