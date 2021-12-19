package com.iuh.IUHStudent.response.diem;

import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.ResponseInterface;
import com.iuh.IUHStudent.response.ResponseStatus;
import com.iuh.IUHStudent.response.lichHoc.DayOfWeek;
import lombok.Builder;

import java.util.List;

@Builder
public class DiemThiResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<DiemHocKy> data;
}
