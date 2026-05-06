package com.stschool.ecommerce.enums;

public enum ShipmentType {
    STANDARD(49), EXPRESS(99), SAME_DAY(199);

    private final double shippingCost;

    ShipmentType(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public double getShippingCost() {
        return shippingCost;
    }
}
