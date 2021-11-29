package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonHocRepository extends JpaRepository<MonHoc,Integer> {

    @Query("SELECT mh.monHocId, mh.tenMonHoc, mh.moTa from MonHoc mh where mh.tenMonHoc = ?1 ")
    List<Object[]> getMonHocWithName(String tenMonHoc);

    @Query("SELECT mh.monHocId, mh.tenMonHoc, mh.moTa from MonHoc mh join KhoaVien kv on mh.khoaVien.khoaVienId = kv.khoaVienId join ChuyenNganh cn on cn.khoaVien.khoaVienId = kv.khoaVienId where cn.chuyenNganhId = ?1 ")
    List<Object[]> getMonHocWithChuyenNganhID(int chuyenNganhId);
}
