package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class ChuyenNganhNotFoundException extends RuntimeException {
    private final int chuyenNganhId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Chuyên ngành with ID ''{0}'' isn''t available", chuyenNganhId);
    }
}
