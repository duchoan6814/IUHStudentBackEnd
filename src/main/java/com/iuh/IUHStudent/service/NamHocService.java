package com.iuh.IUHStudent.service;

import com.iuh.IUHStudent.entity.NamHoc;

import com.iuh.IUHStudent.exception.NamHocNotFoundException;
import com.iuh.IUHStudent.repository.NamHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.iuh.IUHStudent.repository.NamHocRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    public List<NamHoc> getAllNamHoc() {
        List<NamHoc> _listNamHoc = namHocRepository.findAll();
        return _listNamHoc;
    }

    public List<NamHoc> getNamHocWithStartDate(Date date) {
        List<Integer> _idNamHocRes = namHocRepository.getListNamHocWithStartDate(new java.sql.Date(date.getTime()));

        List<NamHoc> _listNamHoc = new ArrayList<>();

        _idNamHocRes.forEach(i -> {
            NamHoc _namHoc = namHocRepository.findById(i).get();

            _listNamHoc.add(_namHoc);
        });

        return _listNamHoc;
    }

    public List<NamHoc> getNamHocWithEndDate(Date date) {
        List<Integer> _idNamHocRes = namHocRepository.getListNamHocWithEndDate(new java.sql.Date(date.getTime()));

        List<NamHoc> _listNamHoc = new ArrayList<>();

        _idNamHocRes.forEach(i -> {
            NamHoc _namHoc = namHocRepository.findById(i).get();

            _listNamHoc.add(_namHoc);
        });

        return _listNamHoc;
    }

    public List<NamHoc> getNamHocBetweenDate(Date startDate, Date endDate) {
        List<Integer> _idNamHocRes = namHocRepository.getListNamHocBetweenTime(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));

        List<NamHoc> _listNamHoc = new ArrayList<>();

        _idNamHocRes.forEach(i -> {
            NamHoc _namHoc = namHocRepository.findById(i).get();

            _listNamHoc.add(_namHoc);
        });

        return _listNamHoc;
    }

}
