package com.iuh.IUHStudent.exception;

public class BadTokenException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Token không hợp lệ!";
    }
}
