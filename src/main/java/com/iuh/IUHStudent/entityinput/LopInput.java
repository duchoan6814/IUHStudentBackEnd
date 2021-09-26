package com.iuh.IUHStudent.entityinput;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LopInput {
    private long id;
    private String tenLop;
    private String khoaHoc;

}
