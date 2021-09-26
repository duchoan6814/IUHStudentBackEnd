package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "hoc_ky")
public class HocKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hocKyId;

    private int namBatDau;
    private int namKetThuc;
    private String moTa;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = HocPhan.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "hocKy_fk",referencedColumnName = "hocKyId")
    private Set<HocPhan> hocPhans = new HashSet<>();
}
