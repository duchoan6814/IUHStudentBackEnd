package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class UserAlreadyExistsException extends RuntimeException {
    private final String username;

    @Override
    public String getMessage() {
        return MessageFormat.format("Người dùng đã tồn tại với username ''{{0}}''", username);
    }
}
