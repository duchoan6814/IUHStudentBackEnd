package com.iuh.IUHStudent.entityinput;

import com.iuh.IUHStudent.entity.SinhVien;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
