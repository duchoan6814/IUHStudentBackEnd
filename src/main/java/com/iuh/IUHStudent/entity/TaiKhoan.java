package com.app.SupportStudentApp.entity;

import lombok.*;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "sinhVienId")
    private SinhVien sinhVien;

}
