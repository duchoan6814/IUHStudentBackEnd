package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.Lop;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LopResponse implements ResponseInterface{
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private Lop data;

}
