package com.foomoo.awf.pojo;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Gender {

    @XmlEnumValue("Male") MALE("Male"),
    @XmlEnumValue("Female") FEMALE("Female"),
    @XmlEnumValue("Transgender") TRANSGENDER("Transgender"),
    @XmlEnumValue("Non-binary") NON_BINARY("Non-binary");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
