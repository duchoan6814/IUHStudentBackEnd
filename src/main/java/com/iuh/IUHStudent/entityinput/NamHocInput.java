package com.iuh.IUHStudent.entityinput;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NamHocInput {
    private Date startDate;
    private Date endDate;
}
