package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HocKyRepository extends JpaRepository<HocKy,Integer> {

    @Query(
            value = "SELECT hk.hoc_ky_id, hk.so_thu_tu, hk.mo_ta , nh.end_date, nh.start_date \n" +
                    "FROM sinh_vien sv \n" +
                    "JOIN sinhvien_lophocphan sl ON sl.sinh_vien_id = sv.sinh_vien_id \n" +
                    "JOIN lop_hoc_phan lhp ON lhp.lop_hoc_phan_id = sl.lop_hoc_phan_id \n" +
                    "JOIN hoc_phan hp ON hp.hoc_phan_id = lhp.hoc_phan_fk\n" +
                    "JOIN hoc_ky hk ON hk.hoc_ky_id = hp.hoc_ky_fk\n" +
                    "JOIN nam_hoc nh ON nh.nam_hoc_id = hk.nam_hoc_fk \n" +
                    "WHERE sv.sinh_vien_id in (?1)\n" +
                    "GROUP BY hk.hoc_ky_id ",
            nativeQuery = true
    )
    List<Object[]> findHocKyBySinhVien(int sinhVienId);
}
