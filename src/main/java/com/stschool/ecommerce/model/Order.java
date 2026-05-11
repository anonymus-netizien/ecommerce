package com.stschool.ecommerce.model;

import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_status")
    private OrderStatus status;
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
}
