package com.example.paymentservice.infrastructure.persistence.jpa;

import com.example.paymentservice.domain.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentJpaMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static  PaymentJpaEntity toEntity(Payment p){

    }

}
