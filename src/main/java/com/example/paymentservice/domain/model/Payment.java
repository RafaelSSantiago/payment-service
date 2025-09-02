package com.example.paymentservice.domain.model;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;


public class Payment {
    private final PaymentId id;
    private final UserId userId;
    private final PaymentMethod method;
    private final Money amount;
    private final String description;
    private PaymentStatus status;
    private String externalId;
    private String failureCode;
    private final Instant createdAt;
    private Instant updatedAt;
    private final Instant expiresAt;
    private final Map<String, Object> metadata;

    private Payment(PaymentId id,
                    UserId userId,
                    PaymentMethod method,
                    Money amount,
                    String description,
                    Instant expiresAt,
                    Map<String, Object> metadata){
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.method = Objects.requireNonNull(method);
        this.amount = Objects.requireNonNull(amount);
        this.description = description;
        this.status = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.expiresAt = expiresAt;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static Payment create(PaymentId id, UserId userId, PaymentMethod method, Money amount, String description, Instant expireAt, Map<String, Object> metadata){
        return new Payment(id, userId, method, amount, description, expireAt, metadata);
    }

    public void startProccessing(){
        ensureStatus(PaymentStatus.PENDING);
        this.status = PaymentStatus.PROCESSING;
        this.updatedAt = Instant.now();
    }


    public void markSuccess(String externalId){
        if(this.status == PaymentStatus.SUCCESS) return;
        if(this.status == PaymentStatus.FAILED)
            throw new IllegalStateException("Cannot succeed a failed payment");
        this.status = PaymentStatus.SUCCESS;
        this.externalId = externalId;
        this.updatedAt = Instant.now();
    }

    public void markFailed(String failureCode){
        if(this.status == PaymentStatus.SUCCESS)
            throw new IllegalStateException("Cannot fail a successful payment");
        this.status = PaymentStatus.FAILED;
        this.failureCode = failureCode;
        this.updatedAt = Instant.now();
    }

    private void ensureStatus(PaymentStatus expected){
        if(this.status != expected){
            throw new IllegalStateException("Invalid transation: expected " + expected + "but was " + this.status);
        }
    }

    public PaymentId id() {return id;}
    public UserId userId() {return userId;}
    public PaymentMethod method() {return method;}
    public Money amount() {return amount;}
    public String description() {return description;}
    public PaymentStatus status() {return status;}
    public String externalId() {return externalId;}
    public String failureCode() {return failureCode;}
    public Instant createdAt() {return createdAt;}
    public Instant updatedAt() {return updatedAt;}
    public Instant expiresAt() {return expiresAt;}
    public Map<String, Object> metadata() {return metadata;}

}
