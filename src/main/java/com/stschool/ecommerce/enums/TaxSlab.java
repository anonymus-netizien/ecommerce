package com.stschool.ecommerce.enums;

public enum TaxSlab {
    LOW(5),
    MEDIUM(12),
    HIGH(18);

    private final double taxRate;

    TaxSlab(double taxRate) {
        this.taxRate = taxRate;
    }

    public double calculateTax(double price) {
        return price * taxRate / 100;
    }
}
