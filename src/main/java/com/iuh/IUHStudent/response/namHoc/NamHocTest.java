package com.iuh.IUHStudent.response.namHoc;

import com.iuh.IUHStudent.entity.HocKy;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class NamHocTest {
    private int namHocId;
    private Date startDate;
    private Date endDate;
    private List<HocKy> hocKys;
}
