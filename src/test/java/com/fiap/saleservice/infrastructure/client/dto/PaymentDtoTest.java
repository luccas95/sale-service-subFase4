package com.fiap.saleservice.infrastructure.client.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentDtoTest {

    @Test
    void shouldCreatePaymentRequestWithValues() {
        Long saleId = 1L;
        double value = 25000.0;

        PaymentRequest request = new PaymentRequest();
        request.setSaleId(saleId);
        request.setValue(value);

        assertEquals(saleId, request.getSaleId());
        assertEquals(value, request.getValue());
    }

    @Test
    void shouldCreatePaymentResponseWithValues() {
        Long id = 10L;
        Long saleId = 1L;
        double saleValue = 25000.0;
        String status = "APROVADO";

        PaymentResponse response = new PaymentResponse();
        response.setId(id);
        response.setSaleId(saleId);
        response.setSaleValue(saleValue);
        response.setStatus(status);

        assertEquals(id, response.getId());
        assertEquals(saleId, response.getSaleId());
        assertEquals(saleValue, response.getSaleValue());
        assertEquals(status, response.getStatus());
    }
}
