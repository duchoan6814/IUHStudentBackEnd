package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SinhVienLopHocPhanPK implements Serializable {
    private int sinhVienId;
    private int lopHocPhanId;

}
