package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "phong_hoc")
public class PhongHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phongHocId;

    private String tenPhongHoc;
    private int sucChua;

    @ManyToOne
    @JoinColumn(name = "dayNha_fk")
    private DayNha dayNha;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = LichHoc.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "phongHoc_fk",referencedColumnName = "phongHocId")
    private Set<LichHoc> lichHocs = new HashSet<>();
}
