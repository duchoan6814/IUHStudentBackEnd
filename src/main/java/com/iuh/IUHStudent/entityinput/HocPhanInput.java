package com.iuh.IUHStudent.entityinput;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HocPhanInput {
    private String maHocPhan;
    private String soTinChiLyThuyet;
    private String getSoTinChiThucHanh;
    private String moTa;
    private boolean batBuoc;
}
