package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SinhVienRepository extends JpaRepository<SinhVien,Integer> {
    Optional<SinhVien> findSinhVienByMaSinhVien(String maSinhVien);

    @Query(
            value = "select sum(mh.so_tin_chi_ly_thuyet) + sum(mh.so_tin_chi_thuc_hanh) as soTinChiThucHanh \n" +
                    "from sinh_vien sv\n" +
                    "join sinhvien_lophocphan sl on sl.sinh_vien_id = sv.sinh_vien_id\n" +
                    "join lop_hoc_phan lhp on lhp.lop_hoc_phan_id = sl.lop_hoc_phan_id\n" +
                    "join hoc_phan hp on hp.hoc_phan_id = lhp.hoc_phan_fk\n" +
                    "join mon_hoc mh on mh.mon_hoc_id = hp.mon_hoc_fk\n" +
                    "where sv.sinh_vien_id in (?1)",
            nativeQuery = true
    )
    List<Integer> getSoTinChiSinhVienDatDuoc(int sinhVienId);

    @Query(
            value = "select sum(mh.so_tin_chi_ly_thuyet) + sum(mh.so_tin_chi_thuc_hanh) as soTinChi \n" +
                    "from sinh_vien sv \n" +
                    "join lop l on sv.lop_fk = l.lop_id \n" +
                    "join chuyen_nganh cn on l.chuyen_nganh_fk =cn.chuyen_nganh_id \n" +
                    "JOIN chuyen_nganh_mon_hoc cnmh on cnmh.chuyen_nganh_id = cn.chuyen_nganh_id\n" +
                    "JOIN mon_hoc mh ON mh.mon_hoc_id = cnmh.mon_hoc_id\n" +
                    "where sv.sinh_vien_id in (?1)",
            nativeQuery = true
    )
    List<Integer> getTongTinChiBySinhVien(int sinhVienID);

    @Query(value = "SELECT mh.ten_mon_hoc, mh.so_tin_chi_ly_thuyet,mh.so_tin_chi_thuc_hanh\n" +
            "FROM sinh_vien sv\n" +
            "join sinhvien_lophocphan sl on sl.sinh_vien_id = sv.sinh_vien_id\n" +
            "join lop_hoc_phan lhp on lhp.lop_hoc_phan_id = sl.lop_hoc_phan_id\n" +
            "join hoc_phan hp on hp.hoc_phan_id = lhp.hoc_phan_fk\n" +
            "JOIN hoc_ky hk on hk.hoc_ky_id = hp.hoc_ky_fk\n" +
            "join mon_hoc mh on mh.mon_hoc_id = hp.mon_hoc_fk\n" +
            "WHERE sv.sinh_vien_id in (93) and hk.hoc_ky_id in (6)",
            nativeQuery = true
    )
    List<Object[]> getListMonHocOfSinhVienByHocKy(int sinhVienId, int hocKyId);

    @Query("select sv.sinhVienId, sv.maSinhVien,sv.maHoSo,sv.image,sv.hoTenDem,sv.ten,sv.gioiTinh,sv.ngaySinh,sv.bacDaoTao,sv.trangThai,sv.loaiHinhDaoTao,sv.ngayVaoTruong,sv.ngayVaoDoan,sv.soDienThoai,sv.diaChi,sv.noiSinh,sv.hoKhauThuongTru,sv.danToc,sv.ngayVaoDang,sv.email,sv.tonGiao from SinhVien sv join Lop l on sv.lop.lopId = l.lopId join ChuyenNganh cn on l.chuyenNganh.chuyenNganhId =cn.chuyenNganhId where cn.khoaVien.khoaVienId = ?1")
    List<Object[]> getSinhVienWithKhoaVienId(int khoaVienId);

    @Query("select sv.sinhVienId, sv.maSinhVien,sv.maHoSo,sv.image,sv.hoTenDem,sv.ten,sv.gioiTinh,sv.ngaySinh,sv.bacDaoTao,sv.trangThai,sv.loaiHinhDaoTao,sv.ngayVaoTruong,sv.ngayVaoDoan,sv.soDienThoai,sv.diaChi,sv.noiSinh,sv.hoKhauThuongTru,sv.danToc,sv.ngayVaoDang,sv.email,sv.tonGiao from SinhVien sv join Lop l on sv.lop.lopId = l.lopId join ChuyenNganh cn on l.chuyenNganh.chuyenNganhId =cn.chuyenNganhId where cn.khoaVien.khoaVienId = ?1 and substring( sv.ngayVaoTruong,1,4 ) = ?2")
    List<Object[]> getSinhVienWithKhoaVienIdAndNgayVaoTruong(@Param("khoaVienId") int khoaVienId, @Param("ngayVaoTruong") String ngayVaoTruong);

    @Query("select distinct substring(sv.ngayVaoTruong,1,4) from SinhVien sv join Lop l on sv.lop.lopId = l.lopId join ChuyenNganh cn on l.chuyenNganh.chuyenNganhId =cn.chuyenNganhId where cn.khoaVien.khoaVienId = ?1")
    List<String> getNamHocWithKhoaVienId(int khoaVienId);
}
