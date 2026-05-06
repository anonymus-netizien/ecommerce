package com.stschool.ecommerce.model;

import com.stschool.ecommerce.enums.OrderStatus;
import com.stschool.ecommerce.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private int id;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
}
