package com.stschool.ecommerce.enums;

public enum UserRole {
    ADMIN("Full access to system"),
    CUSTOMER("Can browse and purchase products"),
    SELLER("Can manage and sell products"),
    ;

    private final String getDescription;

    UserRole(String getDescription) {
        this.getDescription = getDescription;
    }

    public String getDescription() {
        return getDescription;
    }
}
