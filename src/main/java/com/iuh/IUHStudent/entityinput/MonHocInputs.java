package com.iuh.IUHStudent.entityinput;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonHocInputs {
    private String tenMonHoc;
    private String moTa;
    private int chuyenNganhId;
}
