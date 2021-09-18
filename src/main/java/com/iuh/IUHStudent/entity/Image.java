package com.iuh.IUHStudent.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Image {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image() {
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                '}';
    }

    public Image(String name) {
        this.name = name;
    }
}
