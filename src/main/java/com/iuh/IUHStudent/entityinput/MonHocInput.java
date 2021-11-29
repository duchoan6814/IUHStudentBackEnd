package com.iuh.IUHStudent.entityinput;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonHocInput {
    private int monHocId;
    private String tenMonHoc;
    private String moTa;

}
