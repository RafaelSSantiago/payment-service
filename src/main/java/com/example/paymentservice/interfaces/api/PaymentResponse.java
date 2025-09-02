package com.example.paymentservice.interfaces.api;

public record PaymentResponse(
        String id, String status, String method,
        String amount, String currency, String description, String creadedAt
) {}
