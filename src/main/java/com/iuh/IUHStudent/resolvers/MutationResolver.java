package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.Image;
import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.RegisterAccountInput;
import com.iuh.IUHStudent.entityinput.account_input.UpdatePasswordInput;
import com.iuh.IUHStudent.exception.UserAlreadyExistsException;
import com.iuh.IUHStudent.response.AccountResponse;
import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.RegisterResponse;
import com.iuh.IUHStudent.response.ResponseStatus;
import com.iuh.IUHStudent.service.AccountService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("isAuthenticated()")
    public AccountResponse changePassword(UpdatePasswordInput inputs) {
        try {
            Account account = accountService.updatePassword(accountService.getCurrentAccount().getId(), inputs);
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đổi mật khẩu thành công!")
                    .data(account)
                    .build();
        } catch (BadCredentialsException ex) {
            return AccountResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Đổi mật khẩu không thành công!")
                    .errors(new ArrayList<ErrorsResponse>() {{
                        add(ErrorsResponse.builder().message("Mật khẩu không đúng!").build());
                    }})
                    .build();
        }
    }

    @PreAuthorize("isAnonymous()")
    public RegisterResponse register(RegisterAccountInput inputs) {
        try {
            List<Image> images = new ArrayList<>();

            if (inputs.getUser().getImages() != null) {
                for (String name : inputs.getUser().getImages()) {
                    images.add(new Image(name));
                }
            }
            accountService.createAccount(
                    User.builder()
                            .name(inputs.getUser().getName())
                            .email(inputs.getUser().getEmail())
                            .images(images)
                            .build(), AccountInput
                            .builder()
                            .userName(inputs.getUsername())
                            .password(inputs.getPassword())
                            .build()
            );
            return RegisterResponse.builder()
                    .status(ResponseStatus.OK)
                    .message("Tạo tài khỏan thành công.")
                    .build();
        } catch (UserAlreadyExistsException ex) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Tạo tài khỏan không thành công.")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(ErrorsResponse.builder().message("Tài khỏan đã tồn tại!").build());
                        }
                    })
                    .build();
        }


    }
}
