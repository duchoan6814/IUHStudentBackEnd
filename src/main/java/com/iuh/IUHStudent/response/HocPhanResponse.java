package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.HocPhan;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HocPhanResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private HocPhan data;
}
