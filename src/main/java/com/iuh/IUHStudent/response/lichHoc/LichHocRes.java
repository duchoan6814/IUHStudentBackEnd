package com.iuh.IUHStudent.response.lichHoc;

import lombok.Builder;

@Builder
public class LichHocRes {
    private String tenMonHoc;
    private String tenLopHocPhan;
    private String lopHocPhan;
    private String tiet;
    private String phong;
    private String giangVien;
    private String ghiChu;
    private Integer nhomThucHanh;
}
