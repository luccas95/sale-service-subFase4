package com.fiap.saleservice.infrastructure.controller.dto;

import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.domain.entity.SaleStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSaleResponseTest {

    @Test
    public void shouldCreateResponseCorrectly() {
        Sale sale = new Sale();
        sale.setVehicleId(10L);
        sale.setBuyer("Lucas");
        sale.setSaleValue(10000.0);
        sale.setSaleDate(java.time.LocalDateTime.now());
        sale.setStatus(SaleStatus.AGUARDANDO_PAGAMENTO);

        Long paymentId = 123L;

        CreateSaleResponse response = new CreateSaleResponse(sale, paymentId);

        assertNotNull(response.getSale());
        assertEquals(sale, response.getSale());
        assertEquals(paymentId, response.getPaymentId());
    }
}
