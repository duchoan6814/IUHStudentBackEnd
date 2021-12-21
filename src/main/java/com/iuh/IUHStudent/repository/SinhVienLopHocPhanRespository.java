package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.SinhVienLopHocPhan;
import com.iuh.IUHStudent.entity.SinhVienLopHocPhanPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SinhVienLopHocPhanRespository extends JpaRepository<SinhVienLopHocPhan, SinhVienLopHocPhanPK> {

    @Query(
            value = "SELECT lhp.lop_hoc_phan_id \n" +
                    "FROM sinh_vien sv \n" +
                    "JOIN sinhvien_lophocphan sl ON sl.sinh_vien_id = sv.sinh_vien_id \n" +
                    "JOIN lop_hoc_phan lhp ON lhp.lop_hoc_phan_id = sl.lop_hoc_phan_id \n" +
                    "JOIN hoc_phan hp ON hp.hoc_phan_id = lhp.hoc_phan_fk\n" +
                    "JOIN hoc_ky hk ON hk.hoc_ky_id = hp.hoc_ky_fk\n" +
                    "JOIN nam_hoc nh ON nh.nam_hoc_id = hk.nam_hoc_fk\n" +
                    "JOIN mon_hoc mh ON mh.mon_hoc_id = hp.mon_hoc_fk \n" +
                    "WHERE sv.sinh_vien_id in (?1) AND hk.hoc_ky_id in (?2)",
            nativeQuery = true
    )
    List<Object[]> getLopHocPhanOfSinhVienByHocKy(int sinhVienId, int hocKyId);

    @Query(
            value = "SELECT sl.lop_hoc_phan_id, sl.sinh_vien_id\n" +
                    "FROM lop_hoc_phan lhp \n" +
                    "JOIN sinhvien_lophocphan sl on sl.lop_hoc_phan_id = lhp.lop_hoc_phan_id\n" +
                    "WHERE lhp.lop_hoc_phan_id in (?1)",
            nativeQuery = true
    )
    List<Object[]> getListSinhVienLopHocPhanByLopHocPhanId(int lopHocPhan);

}
