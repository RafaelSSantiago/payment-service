package com.example.paymentservice.domain.application.usecase;

import com.example.paymentservice.domain.application.dto.CreatePaymentCommand;
import com.example.paymentservice.domain.application.dto.MarkPaymentFailedCommand;
import com.example.paymentservice.domain.application.dto.MarkPaymentSuccessCommand;
import com.example.paymentservice.domain.application.dto.PaymentView;
import com.example.paymentservice.domain.model.Money;
import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import com.example.paymentservice.domain.model.UserId;
import com.example.paymentservice.domain.model.*;
import com.example.paymentservice.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PaymentApplicationService {
    private final PaymentRepository repository;

    public PaymentApplicationService(PaymentRepository repository){
        this.repository = repository;
    }

    public PaymentView create(CreatePaymentCommand cmd){
        var id = PaymentId.newId();
        var payment = Payment.create(
                id,
                new UserId(cmd.userId()),
                cmd.method(),
                new Money(cmd.amount(), Currency.getInstance(cmd.currency())),
                cmd.description(),
                cmd.expiresAt(),
                cmd.metadata()
        );
        repository.save(payment);
        return  toView(payment);
    }

    public PaymentView get(String id){
        var paymentId = new PaymentId(java.util.UUID.fromString(id));
        var payment = repository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return  toView(payment);
    }

    public PaymentView markSucess(MarkPaymentSuccessCommand cmd) {
        var paymentId = new PaymentId(java.util.UUID.fromString(cmd.paymentId()));
        var payment = repository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.markSuccess(cmd.externalId());
        repository.save(payment);
        return  toView(payment);
    }

    public PaymentView markFailed(MarkPaymentFailedCommand cmd){
        var paymentId = new PaymentId(java.util.UUID.fromString(cmd.paymentId()));
        var payment = repository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.markFailed(cmd.failureCode());
        repository.save(payment);
        return  toView(payment);
    }

    private PaymentView toView(Payment p){
        return new PaymentView(
                p.id().asString(),
                p.status().name(),
                p.method().name(),
                p.amount().amount().toPlainString(),
                p.amount().currency().getCurrencyCode(),
                p.description(),
                p.createdAt().toString()
        );
    }
}
