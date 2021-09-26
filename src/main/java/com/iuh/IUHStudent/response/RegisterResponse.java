package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.SinhVien;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RegisterResponse implements ResponseInterface{
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private SinhVien data;
}
