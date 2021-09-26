package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cmnd")
public class CMND {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmndId;

    private String noiCap;
    private Date ngayCap;

    @OneToOne
    @JoinColumn(name = "sinhVienId")
    private SinhVien sinhVien;
}
