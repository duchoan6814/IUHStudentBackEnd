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
@Table(name = "giang_vien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int giangVienId;

    private String hoTenDem;
    private String ten;
    private String avata;
    private boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private HocHam hocHam;


    @OneToMany(fetch = FetchType.EAGER,targetEntity = Lop.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "giangVien_fk",referencedColumnName = "giangVienId")
    private Set<Lop> lops = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "giangViens")
    private Set<LopHocPhan> lopHocPhans = new HashSet<>();
}
