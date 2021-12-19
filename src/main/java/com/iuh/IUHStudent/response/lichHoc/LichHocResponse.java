package com.iuh.IUHStudent.response.lichHoc;

import com.iuh.IUHStudent.entity.LopHocPhan;
import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.ResponseInterface;
import com.iuh.IUHStudent.response.ResponseStatus;
import lombok.Builder;

import java.util.List;

@Builder
public class LichHocResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<DayOfWeek> data;
}
