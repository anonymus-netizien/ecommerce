package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final List<Order> orders;

    public OrderRepository() {
        this.orders = new ArrayList<>();
    }

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findById(int id) {
        return this.orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    public Order save(Order order) {
        this.orders.add(order);
        return order;
    }

    public Optional<Order> update(int id, OrderStatus newStatus) {
        return this.orders.stream().filter(order -> order.getId() == id).findFirst().map(order -> {
            order.setStatus(newStatus);
            return order;
        });
    }

    public boolean delete(int id) {
        return this.orders.removeIf(order -> order.getId() == id);
    }
}
