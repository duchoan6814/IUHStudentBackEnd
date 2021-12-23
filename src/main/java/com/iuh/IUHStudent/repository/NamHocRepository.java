package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.NamHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface NamHocRepository extends JpaRepository<NamHoc, Integer> {

    @Query(
            value = "SELECT nh.nam_hoc_id \n" +
                    "FROM nam_hoc nh\n" +
                    "WHERE nh.start_date >= ?1 AND nh.end_date <= ?2",
            nativeQuery = true
    )
    List<Integer> getListNamHocBetweenTime(Date startDate, Date endDate);

    @Query(
            value = "SELECT nh.nam_hoc_id \n" +
                    "FROM nam_hoc nh\n" +
                    "WHERE nh.start_date >= ?1",
            nativeQuery = true
    )
    List<Integer> getListNamHocWithStartDate(Date startDate);

    @Query(
            value = "SELECT nh.nam_hoc_id \n" +
                    "FROM nam_hoc nh\n" +
                    "WHERE nh.end_date <= ?1",
            nativeQuery = true
    )
    List<Integer> getListNamHocWithEndDate(Date endDate);
}
