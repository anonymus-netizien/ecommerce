package com.stschool.ecommerce.service;

import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.model.Order;
import com.stschool.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Map<OrderStatus, Long> countOrdersByStatus() {
        return orderRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
    }
}
