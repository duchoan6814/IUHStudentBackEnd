package com.iuh.IUHStudent.response.khoa;

import com.iuh.IUHStudent.entity.KhoaVien;
import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.ResponseStatus;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KhoasResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<KhoaVien> data;
}
