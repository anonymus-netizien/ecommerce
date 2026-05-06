package com.stschool.ecommerce.service;

import com.stschool.ecommerce.enums.OrderStatus;

import java.util.Map;

public interface OrderService {

    Map<OrderStatus, Long> countOrdersByStatus();
}
