package com.iuh.IUHStudent.exception;

import java.text.MessageFormat;

public class LopNotFoundException extends RuntimeException{
    private final int lopId;

    public LopNotFoundException(int lopId) {
        this.lopId = lopId;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Khong tim thay lop ", lopId);
    }
}
