package com.iuh.IUHStudent.response;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.User;
import lombok.Builder;

import java.util.List;

@Builder
public class AccountResponse implements ResponseInterface{
    private ResponseStatus status;
    private List<ErrorsResponse> errors;
    private String message;
    private Account data;

    public AccountResponse() {
    }

    public AccountResponse(ResponseStatus status, List<ErrorsResponse> errors, String message, Account data) {
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

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "status=" + status +
                ", errors=" + errors +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
