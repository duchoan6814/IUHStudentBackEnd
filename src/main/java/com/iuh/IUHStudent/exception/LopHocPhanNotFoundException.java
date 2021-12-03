package com.iuh.IUHStudent.exception;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class LopHocPhanNotFoundException extends RuntimeException  {
    private final int lopHocPhanId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Lớp học phần with ID ''{0}'' isn''t available", lopHocPhanId);
    }
}
