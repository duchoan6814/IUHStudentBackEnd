package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.Lop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LopRepository extends JpaRepository<Lop , Integer> {
}
