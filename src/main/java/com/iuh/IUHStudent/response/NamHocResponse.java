package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.NamHoc;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NamHocResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private NamHoc data;
}
