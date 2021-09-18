package com.iuh.IUHStudent.entityinput;

public class ImageInput {
    private String name;

    public ImageInput() {
    }

    public ImageInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImageInput{" +
                "name='" + name + '\'' +
                '}';
    }
}
