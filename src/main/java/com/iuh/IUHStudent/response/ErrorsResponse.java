package com.iuh.IUHStudent.response;

import java.util.List;

public class ErrorsResponse {
    private String message;
    private List<String> error_fields;

    public ErrorsResponse() {
    }

    public ErrorsResponse(String message) {
        this.message = message;
    }

    public ErrorsResponse(String message, List<String> error_fields) {
        this.message = message;
        this.error_fields = error_fields;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getError_fields() {
        return error_fields;
    }

    public void setError_fields(List<String> error_fields) {
        this.error_fields = error_fields;
    }

    @Override
    public String toString() {
        return "ErrorsResponse{" +
                "message='" + message + '\'' +
                ", error_fields=" + error_fields +
                '}';
    }
}
