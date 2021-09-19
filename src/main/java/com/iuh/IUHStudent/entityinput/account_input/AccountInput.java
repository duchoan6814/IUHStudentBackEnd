package com.iuh.IUHStudent.entityinput.account_input;

import lombok.Builder;

@Builder
public class AccountInput {
    private String userName;
    private String password;

    public AccountInput() {
    }

    public AccountInput(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountInput{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
