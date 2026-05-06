package com.stschool.ecommerce.enums;

public enum OrderPriority {
    LOW(5),
    MEDIUM(3),
    HIGH(1);

    private final int deliveryDays;

    OrderPriority(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public int getDeliveryDays() {
        return this.deliveryDays;
    }
}
