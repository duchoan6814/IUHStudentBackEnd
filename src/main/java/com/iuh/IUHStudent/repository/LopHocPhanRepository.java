package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.LopHocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LopHocPhanRepository extends JpaRepository<LopHocPhan,Integer> {
    @Query("select lhp.lopHocPhanId, lhp.maLopHocPhan,lhp.tenVietTat,lhp.tenLopHocPhan,lhp.soNhomThucHanh,lhp.trangThaiLopHocPhan,lhp.soLuongToiDa,lhp.moTa from LopHocPhan lhp join HocPhan hp on lhp.hocPhan.hocPhanId = hp.hocPhanId join MonHoc mh on hp.monHoc.monHocId = mh.monHocId where mh.khoaVien.khoaVienId = ?1")
    List<Object[]> findLopHocPhanByKhoaVienId(int khoaVienId);
}
