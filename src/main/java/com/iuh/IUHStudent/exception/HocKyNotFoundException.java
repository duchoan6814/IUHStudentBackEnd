package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class HocKyNotFoundException extends RuntimeException{
    private final int hocKyId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Học Kỳ with ID ''{0}'' isn''t available", hocKyId);
    }
}
