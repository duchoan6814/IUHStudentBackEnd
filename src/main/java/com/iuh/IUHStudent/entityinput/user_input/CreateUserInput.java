package com.iuh.IUHStudent.entityinput.user_input;

import com.iuh.IUHStudent.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class CreateUserInput {
    private String name;
    private String email;
    private List<String> images;
}
