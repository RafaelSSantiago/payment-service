package com.example.paymentservice.domain.application.dto;

public record MarkPaymentSuccessCommand(String paymentId, String externalId) {}
