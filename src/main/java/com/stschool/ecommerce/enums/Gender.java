package com.stschool.ecommerce.enums;

public enum Gender {
    MALE, FEMALE, OTHER;

    public String getSalutation() {
        return switch (this) {
            case MALE -> "Mr.";
            case FEMALE -> "Ms.";
            default -> "";
        };
    }
}
