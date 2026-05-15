package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.PaymentDto;
import com.stschool.ecommerce.entity.Payment;
import com.stschool.ecommerce.enums.PaymentMethod;
import com.stschool.ecommerce.enums.PaymentStatus;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentDto save(Payment payment);

    PaymentDto getById(int id);

    List<PaymentDto> getAll();

    PaymentDto update(Payment payment);

    void deleteById(int id);

    PaymentDto getByTransactionId(String transactionId);

    List<PaymentDto> getByPaymentMethod(PaymentMethod paymentMethod);

    List<PaymentDto> getByPaymentStatus(PaymentStatus paymentStatus);

    Map<PaymentStatus, Long> countPaymentsByStatus();
}
