package com.iuh.IUHStudent.entityinput.user_input;

import com.iuh.IUHStudent.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
public class CreateUserInput {
    private String name;
    private String email;
    private List<String> images;
}
