package com.example.paymentservice.domain.application.dto;

public record MarkPaymentFailedCommand(String paymentId, String failureCode) {}
