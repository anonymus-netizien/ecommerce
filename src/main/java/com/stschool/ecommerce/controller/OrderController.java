package com.stschool.ecommerce.controller;

import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/count-by-status")
    public Map<OrderStatus, Long> countOrdersByStatus() {
        return orderService.countOrdersByStatus();
    }
}
