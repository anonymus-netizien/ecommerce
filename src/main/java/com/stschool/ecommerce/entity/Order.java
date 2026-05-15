package com.stschool.ecommerce.entity;

import com.stschool.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id", unique = true,
            nullable = false)
    private String orderId;
    @Column(name = "ordered_date")
    private LocalDateTime orderedDate;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "total_items")
    private int totalItems;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column
    private LocalDateTime expectedDeliveryDate;

    @OneToOne(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )

    private Payment payment;
}
