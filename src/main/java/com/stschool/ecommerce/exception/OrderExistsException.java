package com.stschool.ecommerce.exception;

public class OrderExistsException extends RuntimeException {
    public OrderExistsException(String message) {
        super(message);
    }
}
