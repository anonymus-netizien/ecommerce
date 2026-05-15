package com.stschool.ecommerce.dto;

import com.stschool.ecommerce.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Integer id;
    private String transactionId;
    private PaymentMethod paymentMethod;
    private double amountPaid;
    private String paymentStatus;
    private LocalDateTime paymentDate;
}
