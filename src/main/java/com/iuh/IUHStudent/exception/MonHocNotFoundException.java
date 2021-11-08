package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class MonHocNotFoundException extends RuntimeException {
    private final int monHocId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Môn Học with ID ''{0}'' isn''t available", monHocId);
    }
}
