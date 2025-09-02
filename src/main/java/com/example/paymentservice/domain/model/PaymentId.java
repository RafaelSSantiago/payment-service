package com.example.paymentservice.domain.model;

import java.util.UUID;

public record PaymentId(UUID value) {
    public static PaymentId newId() {return new PaymentId(UUID.randomUUID());}
    public String asString() {return value.toString();}
}
