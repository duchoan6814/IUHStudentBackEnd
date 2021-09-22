package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.SinhVienRepository;
import com.iuh.IUHStudent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

//    public SinhVien getById(Integer id) {
//        return sinhVienRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
//    }
}
