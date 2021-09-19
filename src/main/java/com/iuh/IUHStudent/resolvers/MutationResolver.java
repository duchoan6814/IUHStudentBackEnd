package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.Image;
import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.RegisterAccountInput;
import com.iuh.IUHStudent.exception.UserAlreadyExistsException;
import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.RegisterResponse;
import com.iuh.IUHStudent.response.ResponseStatus;
import com.iuh.IUHStudent.service.AccountService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AccountService accountService;

    public RegisterResponse register(RegisterAccountInput inputs) {
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


    }
}
