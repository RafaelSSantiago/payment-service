package com.example.paymentservice.interfaces.api;

import com.example.paymentservice.domain.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record CreatePaymentRequest(
        @NotNull UUID userId,
        @NotNull PaymentMethod method,
        @NotNull @DecimalMin(value = "0.1") BigDecimal amount,
        @NotBlank String currency,
        @Size(max = 255) String description,
        Instant expiresAt,
        Map<String, Object> metadata
        ) { }
