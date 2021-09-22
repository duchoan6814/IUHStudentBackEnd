package com.iuh.IUHStudent.response;

import java.util.List;

public class DeleteUserResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;

    public DeleteUserResponse() {
    }

    public DeleteUserResponse(ResponseStatus status, List<ErrorsResponse> errors, String message) {
        this.status = status;
        this.errors = errors;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public List<ErrorsResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsResponse> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DeleteUserResponse{" +
                "status=" + status +
                ", errors=" + errors +
                ", message='" + message + '\'' +
                '}';
    }
}
