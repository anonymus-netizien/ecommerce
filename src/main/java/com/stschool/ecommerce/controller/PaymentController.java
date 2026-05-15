package com.stschool.ecommerce.controller;

import com.stschool.ecommerce.entity.Payment;
import com.stschool.ecommerce.enums.PaymentMethod;
import com.stschool.ecommerce.enums.PaymentStatus;
import com.stschool.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> savePayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.save(payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @PutMapping
    public ResponseEntity<?> updatePayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.update(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable int id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getPaymentByTransactionId(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getByTransactionId(transactionId));
    }

    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<?> getPaymentsByMethod(@PathVariable PaymentMethod paymentMethod) {
        return ResponseEntity.ok(paymentService.getByPaymentMethod(paymentMethod));
    }

    @GetMapping("/status/{paymentStatus}")
    public ResponseEntity<?> getPaymentsByStatus(@PathVariable PaymentStatus paymentStatus) {
        return ResponseEntity.ok(paymentService.getByPaymentStatus(paymentStatus));
    }

    @GetMapping("/count/by-status")
    public ResponseEntity<?> countPaymentsByStatus() {
        return ResponseEntity.ok(paymentService.countPaymentsByStatus());
    }
}
