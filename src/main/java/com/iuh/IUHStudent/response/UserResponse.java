package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.User;

import java.util.List;

public class UserResponse implements ResponseInterface {
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private List<User> data;

    public UserResponse() {
    }

    public UserResponse(ResponseStatus status, List<ErrorsResponse> errors, String message, List<User> data) {
        this.status = status;
        this.errors = errors;
        this.message = message;
        this.data = data;
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

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "status=" + status +
                ", errors=" + errors +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
