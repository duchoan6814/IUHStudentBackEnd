package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class HocPhanNotFoundException extends RuntimeException{
    private final int hocPhanId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Học phần with ID ''{0}'' isn''t available", hocPhanId);
    }
}
