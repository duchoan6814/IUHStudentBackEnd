package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.MonHoc;
import com.iuh.IUHStudent.entity.NamHoc;
import com.iuh.IUHStudent.exception.MonHocNotFoundException;
import com.iuh.IUHStudent.exception.NamHocNotFoundException;
import com.iuh.IUHStudent.repository.NamHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NamHocService {
    @Autowired
    private NamHocRepository namHocRepository;

    public NamHoc findNamHocById(int namHocId) {
        Optional<NamHoc> result =  namHocRepository.findById(namHocId);
        NamHoc namHoc = null;
        if (result.isPresent()) {
            namHoc = result.get();
        }
        return namHoc;
    }

    public boolean deleteNamHoc(int namHocId) {
        NamHoc namHoc = findNamHocById(namHocId);
        if(namHoc == null){
            throw new NamHocNotFoundException(namHocId);
        }
        namHocRepository.delete(namHoc);

        return true;
    }
}
