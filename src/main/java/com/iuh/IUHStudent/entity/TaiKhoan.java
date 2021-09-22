package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taiKhoanId;

    private String matKhau;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "sinhVienId")
//    private SinhVien sinhViens;

}
