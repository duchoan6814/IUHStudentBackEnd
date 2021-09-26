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
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int monHocId;

    private String tenMonHoc;
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "khoaVien_fk")
    private KhoaVien khoaVien;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = HocPhan.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "monHoc_fk",referencedColumnName = "monHocId")
    private Set<HocPhan> hocPhans;

}
