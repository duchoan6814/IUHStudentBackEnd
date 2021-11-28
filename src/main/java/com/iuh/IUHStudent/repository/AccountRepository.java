package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String userName);

    @Query("select a.username from Account a where a.sinhVien.sinhVienId = ?1")
    List<Object[]> findAccountBySinhVienId(int sinhVienId);
}
