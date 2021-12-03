package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.LopHocPhan;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LopHocPhanRespone {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private LopHocPhan data;
}
