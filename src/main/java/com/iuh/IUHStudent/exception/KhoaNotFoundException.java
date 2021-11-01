package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
@RequiredArgsConstructor
public class KhoaNotFoundException extends RuntimeException{
    private final int khoaId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Khoa with ID ''{0}'' isn''t available", khoaId);
    }
}
