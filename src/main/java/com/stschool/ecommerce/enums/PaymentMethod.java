package com.stschool.ecommerce.enums;

public enum PaymentMethod {
    CREDIT_CARD(true),
    DEBIT_CARD(true),
    UPI(true),
    NET_BANKING(true),
    CASH_ON_DELIVERY(false);

    private final boolean isOnlineProcessingRequired;

    PaymentMethod(boolean isOnlineProcessingRequired) {
        this.isOnlineProcessingRequired = isOnlineProcessingRequired;
    }

    public boolean requiresOnlineProcessing() {
        return isOnlineProcessingRequired;
    }
}
