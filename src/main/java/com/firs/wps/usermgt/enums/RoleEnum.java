package com.firs.wps.usermgt.enums;

public enum RoleEnum {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;

    private RoleEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
