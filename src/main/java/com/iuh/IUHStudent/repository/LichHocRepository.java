package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface LichHocRepository extends JpaRepository<LichHoc, Integer> {

    @Query(
            value = "SELECT\n" +
                    "lh.lich_hoc_id , lh.ghi_chu, lh.ngay_hoc_trong_tuan, lh.thoi_gian_bat_dau, lh.thoi_gian_ket_thuc, lh.tiet_hoc_bat_dau, lh.tiet_hoc_ket_thuc,\n" +
                    "lhp.ma_lop_hoc_phan, lhp.ten_lop_hoc_phan,\n" +
                    "ph.ten_phong_hoc,\n" +
                    "dn.ten_day_nha,\n" +
                    "gv.ho_ten_dem, gv.ten \n" +
                    "FROM lich_hoc lh\n" +
                    "Join lop_hoc_phan lhp ON lh.lop_hoc_phan_fk = lhp.lop_hoc_phan_id\n" +
                    "JOIN sinhvien_lophocphan sl ON sl.lop_hoc_phan_id = lhp.lop_hoc_phan_id\n" +
                    "JOIN phong_hoc ph ON ph.phong_hoc_id = lh.phong_hoc_fk\n" +
                    "JOIN day_nha dn ON dn.day_nha_id = ph.day_nha_fk \n" +
                    "JOIN giangvien_lophocphan gl ON gl.lop_hoc_phan_id = lhp.lop_hoc_phan_id\n" +
                    "JOIN giang_vien gv ON gv.giang_vien_id = gl.giang_vien_id \n" +
                    "WHERE sl.sinh_vien_id in (?1) and (lh.thoi_gian_bat_dau <= ?2 AND lh.thoi_gian_ket_thuc >= ?3)",
            nativeQuery = true

    )
    List<Object[]> getListLichHocBySinhVien(int sinhVienId, Date firstDateOfWeek, Date lateDateOfWeek);
}
