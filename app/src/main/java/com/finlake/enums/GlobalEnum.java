package com.finlake.enums;

public enum GlobalEnum {

    ONE_ON_ONE("one_on_one"), GROUP("group");

    private final String value;

    GlobalEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
