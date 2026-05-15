package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.entity.Payment;
import com.stschool.ecommerce.enums.PaymentMethod;
import com.stschool.ecommerce.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    @Query("SELECT p.paymentStatus, COUNT(p) FROM Payment p GROUP BY p.paymentStatus")
    List<Object[]> countByPaymentStatus();
}
