package com.foomoo.awf.pojo;

public enum  ApplicableCircumstance {
    HOMELESSNESS("Homelessness"),
    SUBSTANCE_MISUSE("Substance Misuse"),
    EATING_DISORDER("Eating Disorder"),
    UNEMPLOYED("Unemployed"),
    HISTORY_OF_OFFENDING("History of Offending"),
    MENTAL_HEALTH_ISSUES("Mental Health Issues"),
    SELF_HARM("Self Harm"),
    ADDITIONAL_LEARNING_NEEDS("Additional Learning Needs"),
    NOT_IN_TRAINING("Not in Training"),
    OTHER("Other");

    private String name;

    private ApplicableCircumstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
