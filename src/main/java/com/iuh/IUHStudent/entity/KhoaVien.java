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
@Table(name = "khoa_vien")
public class KhoaVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int khoaVienId;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = ChuyenNganh.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaVien_fk",referencedColumnName = "khoaVienId")
    private Set<ChuyenNganh> chuyenNganhs = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER,targetEntity = MonHoc.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "khoaVien_fk",referencedColumnName = "khoaVienId")
    private Set<MonHoc> monHocs = new HashSet<>();
}
