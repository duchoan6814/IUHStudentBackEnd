package com.iuh.IUHStudent.entityinput;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HocPhanInput {
    private String maHocPhan;
    private int soTinChiLyThuyet;
    private int getSoTinChiThucHanh;
    private String moTa;
    private boolean batBuoc;
}
