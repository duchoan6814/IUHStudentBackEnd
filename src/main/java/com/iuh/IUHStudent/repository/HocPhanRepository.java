package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.HocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HocPhanRepository extends JpaRepository<HocPhan,Integer> {
    @Query("select hp.hocPhanId,hp.maHocPhan,hp.soTinChiLyThuyet,hp.getSoTinChiThucHanh,hp.moTa,hp.batBuoc from HocPhan hp  join MonHoc mh on hp.monHoc.monHocId = mh.monHocId where mh.khoaVien.khoaVienId = ?1")
    List<Object[]> findHocPhanByKhoaVienId(int khoaVienId);
}
