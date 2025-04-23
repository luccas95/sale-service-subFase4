package com.fiap.saleservice.infrastructure.controller;

import com.fiap.saleservice.application.usecase.CreateSaleUseCase;
import com.fiap.saleservice.application.usecase.ListSalesUseCase;
import com.fiap.saleservice.application.usecase.UpdateSaleStatusUseCase;
import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.domain.entity.SaleStatus;
import com.fiap.saleservice.infrastructure.controller.dto.CreateSaleResponse;
import com.fiap.saleservice.infrastructure.controller.dto.SaleRequest;
import com.fiap.saleservice.infrastructure.controller.dto.SaleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleControllerTest {

    private SaleController controller;
    private CreateSaleUseCase createSaleUseCase;
    private ListSalesUseCase listSalesUseCase;
    private UpdateSaleStatusUseCase updateSaleStatusUseCase;

    @BeforeEach
    public void setUp() {
        createSaleUseCase = mock(CreateSaleUseCase.class);
        listSalesUseCase = mock(ListSalesUseCase.class);
        updateSaleStatusUseCase = mock(UpdateSaleStatusUseCase.class);

        controller = new SaleController(createSaleUseCase, listSalesUseCase, updateSaleStatusUseCase);
    }

    @Test
    public void testCreateSale() {
        SaleRequest request = new SaleRequest();
        request.setVehicleId(10L);
        request.setBuyer("Lucas");

        Sale sale = new Sale();
        sale.setVehicleId(10L);
        sale.setBuyer("Lucas");
        sale.setSaleValue(10000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.AGUARDANDO_PAGAMENTO);

        CreateSaleResponse createSaleResponse = new CreateSaleResponse(sale, 123L);

        when(createSaleUseCase.execute(request.getVehicleId(), request.getBuyer()))
                .thenReturn(createSaleResponse);

        var response = controller.create(request);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Lucas", response.getBody().getBuyer());
        assertEquals(123L, response.getBody().getPaymentId());
    }

    @Test
    public void testListSales() {
        Sale sale = new Sale();
        sale.setVehicleId(10L);
        sale.setBuyer("Lucas");
        sale.setSaleValue(10000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.AGUARDANDO_PAGAMENTO);

        when(listSalesUseCase.execute()).thenReturn(Collections.singletonList(sale));

        var response = controller.list();

        assertEquals(200, response.getStatusCode().value());
        List<SaleResponse> sales = response.getBody();
        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals("Lucas", sales.get(0).getBuyer());
    }

    @Test
    public void testConcluirVenda() {
        Sale sale = new Sale();
        sale.setVehicleId(10L);
        sale.setBuyer("Lucas");
        sale.setSaleValue(10000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.CONCLUIDO);

        when(updateSaleStatusUseCase.execute(1L, SaleStatus.CONCLUIDO)).thenReturn(sale);

        var response = controller.concluirVenda(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(SaleStatus.CONCLUIDO, response.getBody().getStatus());
    }

    @Test
    public void testCancelarVenda() {
        Sale sale = new Sale();
        sale.setVehicleId(20L);
        sale.setBuyer("Carlos");
        sale.setSaleValue(25000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.CANCELADO);

        when(updateSaleStatusUseCase.execute(2L, SaleStatus.CANCELADO)).thenReturn(sale);

        var response = controller.cancelarVenda(2L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(SaleStatus.CANCELADO, response.getBody().getStatus());
    }
}
