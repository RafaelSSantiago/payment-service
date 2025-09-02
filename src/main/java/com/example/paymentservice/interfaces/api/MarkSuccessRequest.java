package com.example.paymentservice.interfaces.api;

import jakarta.validation.constraints.NotBlank;

public record MarkSuccessRequest(@NotBlank String externalId) { }
