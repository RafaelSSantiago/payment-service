package com.example.paymentservice.domain.application.dto;

public record PaymentView(
        String id,
        String status,
        String method,
        String amount,
        String currency,
        String description,
        String createdAt
){}