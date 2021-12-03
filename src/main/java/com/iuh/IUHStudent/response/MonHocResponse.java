package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.MonHoc;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonHocResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private MonHoc data;
}
