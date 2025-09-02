package com.example.paymentservice.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount");
        currency = (currency == null) ? Currency.getInstance("BRL") : currency;
        if(amount.scale() > 2){
            amount = amount.setScale(2, RoundingMode.HALF_UP);
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    public static Money of(String amount, String currency){
        return new Money(new BigDecimal(amount), Currency.getInstance(currency));
    }
}
