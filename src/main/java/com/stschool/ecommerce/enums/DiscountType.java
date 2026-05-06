package com.stschool.ecommerce.enums;

public enum DiscountType {
    PERCENTAGE, FLAT;

    public double applyDiscount(double price, double value) {
        return switch (this) {
            case PERCENTAGE -> price - (price * value / 100);
            case FLAT -> price - value;
        };
    }
}
