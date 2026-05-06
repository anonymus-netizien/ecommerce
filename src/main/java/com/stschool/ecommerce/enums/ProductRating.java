package com.stschool.ecommerce.enums;

public enum ProductRating {
    ONE_STAR(1), TWO_STAR(2), THREE_STAR(3), FOUR_STAR(4), FIVE_STAR(5);

    private final int numericRating;

    ProductRating(int rating) {
        this.numericRating = rating;
    }

    public int getNumericRating() {
        return numericRating;
    }
}