package com.iuh.IUHStudent.response.diem;

import lombok.Builder;

import java.util.List;

@Builder
public class DiemMonHoc {
    private String tenMonHoc;
    private String ghiChu;
    private List<Double> diemThuongKy;
    private Float diemGiuaKy;
    private List<Double> diemThucHanh;
    private Float diemCuoiKy;
    private Double diemTrungBinh;
}
