package com.example.paymentservice.domain.application.dto;

import com.example.paymentservice.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record CreatePaymentCommand(
        UUID userId,
        PaymentMethod method,
        BigDecimal amount,
        String currency,
        String description,
        Instant expiresAt,
        Map<String, Object> metadata
        ) {}

