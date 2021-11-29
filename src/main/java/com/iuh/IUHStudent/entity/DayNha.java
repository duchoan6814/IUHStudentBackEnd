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
@Table(name = "day_nha")
public class DayNha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dayNhaId;

    private String tenDayNha;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = PhongHoc.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "dayNha_fk",referencedColumnName = "dayNhaId")
    private Set<PhongHoc> phongHocs = new HashSet<>();
}
