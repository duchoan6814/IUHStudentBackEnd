package com.iuh.IUHStudent.entityinput.account_input;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdatePasswordInput {
    private final String currentPassword;
    private final String newPassword;
}
