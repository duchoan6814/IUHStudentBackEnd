package com.iuh.IUHStudent.entityinput.account_input;

import com.iuh.IUHStudent.entityinput.SinhVienInput;
import com.iuh.IUHStudent.entityinput.user_input.CreateUserInput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterAccountInput {
    private String username;
    private String password;
    private SinhVienInput sinhVien;


}
