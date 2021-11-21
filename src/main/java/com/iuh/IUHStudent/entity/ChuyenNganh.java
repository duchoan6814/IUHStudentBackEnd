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
@Table(name = "chuyen_nganh")
public class ChuyenNganh{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chuyenNganhId;

    private String tenChuyenNganh;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = Lop.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "chuyenNganh_fk",referencedColumnName = "chuyenNganhId")
    private Set<Lop> lops = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "khoaVien_fk")
    private KhoaVien khoaVien;

}
