package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.entityinput.KhoaInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChuyenNganhRespository extends JpaRepository<ChuyenNganh,Integer> {
    public List<ChuyenNganh> findChuyenNganhsByKhoaVien(KhoaInput khoaVien);

    @Query("SELECT c.chuyenNganhId,c.tenChuyenNganh FROM ChuyenNganh c JOIN KhoaVien k on c.khoaVien.khoaVienId = k.khoaVienId WHERE k.khoaVienId = ?1")
    List<ChuyenNganh> findAllActiveUsers(int khoaVienId);
}
