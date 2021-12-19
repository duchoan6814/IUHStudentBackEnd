package com.iuh.IUHStudent.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "nam_hoc")
public class NamHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int namHocId;

    private Date startDate;
    private Date endDate;

    @OneToMany(fetch = FetchType.EAGER,targetEntity = HocKy.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "nam_hoc_fk", referencedColumnName = "namHocId")
    private Set<HocKy> hocKy;

}
