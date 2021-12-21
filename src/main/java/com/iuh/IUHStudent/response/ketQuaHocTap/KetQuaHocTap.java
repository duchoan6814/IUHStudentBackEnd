package com.iuh.IUHStudent.response.ketQuaHocTap;

import com.iuh.IUHStudent.entity.MonHoc;
import lombok.Builder;

@Builder
public class KetQuaHocTap {
    private MonHoc monHoc;
    private double diem;
    private double diemTrungBinh;
}
