package com.foomoo.awf.pojo;

import javax.xml.bind.annotation.XmlEnumValue;

public enum  ApplicableCircumstance {
    @XmlEnumValue("Homelessness") HOMELESSNESS("Homelessness"),
    @XmlEnumValue("Substance Misuse") SUBSTANCE_MISUSE("Substance Misuse"),
    @XmlEnumValue("Eating Disorder") EATING_DISORDER("Eating Disorder"),
    @XmlEnumValue("Unemployed") UNEMPLOYED("Unemployed"),
    @XmlEnumValue("History of Offending") HISTORY_OF_OFFENDING("History of Offending"),
    @XmlEnumValue("Mental Health Issues") MENTAL_HEALTH_ISSUES("Mental Health Issues"),
    @XmlEnumValue("Self Harm") SELF_HARM("Self Harm"),
    @XmlEnumValue("Additional Learning Needs") ADDITIONAL_LEARNING_NEEDS("Additional Learning Needs"),
    @XmlEnumValue("Not in Training") NOT_IN_TRAINING("Not in Training"),
    @XmlEnumValue("Other") OTHER("Other");

    private String name;

    ApplicableCircumstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
