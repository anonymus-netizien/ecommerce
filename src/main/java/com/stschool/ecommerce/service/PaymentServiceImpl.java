package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.PaymentDto;
import com.stschool.ecommerce.entity.Payment;
import com.stschool.ecommerce.enums.PaymentMethod;
import com.stschool.ecommerce.enums.PaymentStatus;
import com.stschool.ecommerce.exception.PaymentNotFoundException;
import com.stschool.ecommerce.repository.PaymentRepository;
import com.stschool.ecommerce.util.IdGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaymentDto save(Payment payment) {
        try {
            payment.setTransactionId(IdGeneratorUtil.generateTransactionId());

            payment.setPaymentDate(LocalDateTime.now());
            /*
                Setting Default Status
             */
            payment.setPaymentStatus(PaymentStatus.INITIATED);

            Payment savedPayment = paymentRepository.save(payment);

            return modelMapper.map(savedPayment, PaymentDto.class);

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Duplicate Transaction Id generated");
        }
    }

    @Override
    public PaymentDto getById(int id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment with id " + id + " not found"));

        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getAll() {
        return paymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .toList();
    }

    @Override
    public PaymentDto update(Payment payment) {
        Payment existingPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id " + payment.getId() + " not found"));

        payment.setTransactionId(existingPayment.getTransactionId());

        Payment updatedPayment = paymentRepository.save(payment);
        return modelMapper.map(updatedPayment, PaymentDto.class);
    }

    @Override
    public void deleteById(int id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id " + id + " not found"));
        paymentRepository.delete(payment);
    }

    @Override
    public PaymentDto getByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with transactionId " + transactionId + " not found"));

        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getByPaymentMethod(PaymentMethod paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod).stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .toList();
    }

    @Override
    public List<PaymentDto> getByPaymentStatus(PaymentStatus paymentStatus) {
        return paymentRepository.findByPaymentStatus(paymentStatus).stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .toList();
    }

    @Override
    public Map<PaymentStatus, Long> countPaymentsByStatus() {
        return paymentRepository.countByPaymentStatus().stream()
                .collect(Collectors.toMap(row -> (PaymentStatus) row[0], row -> (Long) row[1]));
    }
}
