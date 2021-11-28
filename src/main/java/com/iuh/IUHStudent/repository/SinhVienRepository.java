package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SinhVienRepository extends JpaRepository<SinhVien,Integer> {
    Optional<SinhVien> findSinhVienByMaSinhVien(String maSinhVien);

    @Query("select sv.sinhVienId, sv.maSinhVien,sv.maHoSo,sv.image,sv.hoTenDem,sv.ten,sv.gioiTinh,sv.ngaySinh,sv.bacDaoTao,sv.trangThai,sv.loaiHinhDaoTao,sv.ngayVaoTruong,sv.ngayVaoDoan,sv.soDienThoai,sv.diaChi,sv.noiSinh,sv.hoKhauThuongTru,sv.danToc,sv.ngayVaoDang,sv.email,sv.tonGiao from SinhVien sv join Lop l on sv.lop.lopId = l.lopId join ChuyenNganh cn on l.chuyenNganh.chuyenNganhId =cn.chuyenNganhId where cn.khoaVien.khoaVienId = ?1")
    List<Object[]> getSinhVienWithKhoaVienId(int khoaVienId);

    @Query("select sv.sinhVienId, sv.maSinhVien,sv.maHoSo,sv.image,sv.hoTenDem,sv.ten,sv.gioiTinh,sv.ngaySinh,sv.bacDaoTao,sv.trangThai,sv.loaiHinhDaoTao,sv.ngayVaoTruong,sv.ngayVaoDoan,sv.soDienThoai,sv.diaChi,sv.noiSinh,sv.hoKhauThuongTru,sv.danToc,sv.ngayVaoDang,sv.email,sv.tonGiao from SinhVien sv join Lop l on sv.lop.lopId = l.lopId join ChuyenNganh cn on l.chuyenNganh.chuyenNganhId =cn.chuyenNganhId where cn.khoaVien.khoaVienId = ?1 and substring( sv.ngayVaoTruong,1,4 ) = ?2")
    List<Object[]> getSinhVienWithKhoaVienIdAndNgayVaoTruong(@Param("khoaVienId") int khoaVienId, @Param("ngayVaoTruong") String ngayVaoTruong);

}
