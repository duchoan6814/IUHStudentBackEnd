package com.iuh.IUHStudent.entityinput;

import com.iuh.IUHStudent.entity.TrangThaiLopHocPhan;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LopHocPhanInput {
    private String maLopHocPhan;
    private String tenVietTat;
    private String tenLopHocPhan;
    private int soNhomThucHanh;
    private TrangThaiLopHocPhan trangThaiLopHocPhan;
    private int soLuongToiDa;
    private String moTa;
}
