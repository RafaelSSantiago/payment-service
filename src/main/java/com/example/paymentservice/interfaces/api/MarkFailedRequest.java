package com.example.paymentservice.interfaces.api;

import jakarta.validation.constraints.NotBlank;

public record MarkFailedRequest(@NotBlank String failuredCode) {}
