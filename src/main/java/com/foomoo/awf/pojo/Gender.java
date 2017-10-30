package com.foomoo.awf.pojo;

public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    TRANSGENDER("Transgender"),
    NON_BINARY("Non-binary");

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
