package com.fiap.saleservice.infrastructure.controller.dto;

import com.fiap.saleservice.domain.entity.SaleStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SaleResponseTest {

    @Test
    void shouldCreateSaleResponseAndAccessFields() {
        Long id = 1L;
        Long vehicleId = 10L;
        Double saleValue = 25000.0;
        String buyer = "Lucas";
        LocalDateTime saleDate = LocalDateTime.now();
        SaleStatus status = SaleStatus.CONCLUIDO;
        Long paymentId = 123L;

        SaleResponse response = new SaleResponse(id, vehicleId, saleValue, buyer, saleDate, status, paymentId);

        assertEquals(id, response.getId());
        assertEquals(vehicleId, response.getVehicleId());
        assertEquals(saleValue, response.getSaleValue());
        assertEquals(buyer, response.getBuyer());
        assertEquals(saleDate, response.getSaleDate());
        assertEquals(status, response.getStatus());
        assertEquals(paymentId, response.getPaymentId());
    }

    @Test
    void shouldSetAllFieldsUsingSetters() {
        SaleResponse response = new SaleResponse(null, null, null, null, null, null, null);

        Long id = 2L;
        Long vehicleId = 20L;
        Double saleValue = 30000.0;
        String buyer = "Maria";
        LocalDateTime saleDate = LocalDateTime.now();
        SaleStatus status = SaleStatus.AGUARDANDO_PAGAMENTO;
        Long paymentId = 456L;

        response.setId(id);
        response.setVehicleId(vehicleId);
        response.setSaleValue(saleValue);
        response.setBuyer(buyer);
        response.setSaleDate(saleDate);
        response.setStatus(status);
        response.setPaymentId(paymentId);

        assertEquals(id, response.getId());
        assertEquals(vehicleId, response.getVehicleId());
        assertEquals(saleValue, response.getSaleValue());
        assertEquals(buyer, response.getBuyer());
        assertEquals(saleDate, response.getSaleDate());
        assertEquals(status, response.getStatus());
        assertEquals(paymentId, response.getPaymentId());
    }
}
