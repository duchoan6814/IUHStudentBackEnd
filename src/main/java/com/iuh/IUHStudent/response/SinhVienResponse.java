package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.entity.SinhVien;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SinhVienResponse implements ResponseInterface{
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private SinhVien data;
}
