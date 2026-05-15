package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.entity.Order;
import com.stschool.ecommerce.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    boolean existsByOrderId(String orderId);

    Page<Order> findAllByOrderByOrderedDateDesc(Pageable pageable);
}
