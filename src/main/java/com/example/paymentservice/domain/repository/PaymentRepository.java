package com.example.paymentservice.domain.repository;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository()
public interface PaymentRepository{
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentId id);
}
