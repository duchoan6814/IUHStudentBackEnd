package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.ChuyenNganh;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChuyenNganhsResponse {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<ChuyenNganh> data;
}
