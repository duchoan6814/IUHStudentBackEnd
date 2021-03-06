package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.MonHoc;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonHocsResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<MonHoc> data;
}
