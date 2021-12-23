package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class NamHocNotFoundException extends RuntimeException {
    private final int namHocId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Năm học with ID ''{0}'' isn''t available", namHocId);
    }
}
