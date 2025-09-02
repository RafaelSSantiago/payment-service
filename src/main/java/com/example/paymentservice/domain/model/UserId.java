package com.example.paymentservice.domain.model;

import java.util.UUID;

public record UserId(UUID value) {
    public static UserId of(String raw){return new UserId(UUID.fromString(raw));}
}
