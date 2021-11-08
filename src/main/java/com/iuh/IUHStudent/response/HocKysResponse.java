package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.HocKy;
import com.iuh.IUHStudent.entity.KhoaVien;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HocKysResponse  {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<HocKy> data;
}
