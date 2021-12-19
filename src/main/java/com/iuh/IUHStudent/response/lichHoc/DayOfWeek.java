package com.iuh.IUHStudent.response.lichHoc;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DayOfWeek {
    private String name;
    List<LichHocRes> monHocs;
}
