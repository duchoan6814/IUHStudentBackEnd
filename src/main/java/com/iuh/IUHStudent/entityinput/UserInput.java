package com.iuh.IUHStudent.entityinput;

import java.util.List;

public class UserInput {
    private String name;
    private String email;
    private AccountInput account;
    private List<ImageInput> images;

    public UserInput() {
    }

    public UserInput(String name, String email, AccountInput account, List<ImageInput> images) {
        this.name = name;
        this.email = email;
        this.account = account;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountInput getAccount() {
        return account;
    }

    public void setAccount(AccountInput account) {
        this.account = account;
    }

    public List<ImageInput> getImages() {
        return images;
    }

    public void setImages(List<ImageInput> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "UserInput{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", account=" + account +
                ", images=" + images +
                '}';
    }
}
