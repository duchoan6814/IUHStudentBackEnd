package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.MonHoc;
import com.iuh.IUHStudent.entity.NamHoc;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NamHocsResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<NamHoc> data;
}
