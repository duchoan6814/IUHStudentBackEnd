package com.iuh.IUHStudent.entityinput;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HocKyInput {
    private int namBatDau;
    private int namKetThuc;
    private String moTa;
}
