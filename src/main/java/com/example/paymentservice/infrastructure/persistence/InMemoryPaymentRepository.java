package com.example.paymentservice.infrastructure.persistence;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import com.example.paymentservice.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<UUID, Payment> store = new ConcurrentHashMap<>();


    @Override
    public Payment save(Payment payment){
        store.put(payment.id().value(), payment);
        return payment;
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        return Optional.ofNullable(store.get(id.value()));
    }
}
