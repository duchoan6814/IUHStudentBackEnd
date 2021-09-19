package com.iuh.IUHStudent.entityinput.account_input;

import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.entityinput.user_input.CreateUserInput;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class RegisterAccountInput {
    private String username;
    private String password;
    private CreateUserInput user;

}
