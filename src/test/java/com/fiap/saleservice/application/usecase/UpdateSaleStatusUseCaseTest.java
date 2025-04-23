package com.fiap.saleservice.application.usecase;

import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.domain.entity.SaleStatus;
import com.fiap.saleservice.exception.NotFoundException;
import com.fiap.saleservice.infrastructure.client.VehicleClient;
import com.fiap.saleservice.infrastructure.gateway.SaleGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateSaleStatusUseCaseTest {

    private SaleGateway saleGateway;
    private VehicleClient vehicleClient;
    private UpdateSaleStatusUseCase useCase;

    @BeforeEach
    public void setUp() {
        saleGateway = mock(SaleGateway.class);
        vehicleClient = mock(VehicleClient.class);
        useCase = new UpdateSaleStatusUseCase(saleGateway, vehicleClient);
    }

    @Test
    public void testUpdateToConcluidoShouldMarkVehicleAsSold() {
        Sale sale = new Sale();
        sale.setVehicleId(100L);
        sale.setBuyer("Lucas");
        sale.setSaleValue(50000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.AGUARDANDO_PAGAMENTO);

        when(saleGateway.findById(1L)).thenReturn(Optional.of(sale));
        when(saleGateway.save(any())).thenReturn(sale);

        Sale result = useCase.execute(1L, SaleStatus.CONCLUIDO);

        assertEquals(SaleStatus.CONCLUIDO, result.getStatus());
        verify(vehicleClient).markVehicleAsSold(100L);
        verify(saleGateway).save(sale);
    }

    @Test
    public void testUpdateToCanceladoShouldNotMarkVehicleAsSold() {
        Sale sale = new Sale();
        sale.setVehicleId(101L);
        sale.setBuyer("Ana");
        sale.setSaleValue(30000.0);
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.AGUARDANDO_PAGAMENTO);

        when(saleGateway.findById(2L)).thenReturn(Optional.of(sale));
        when(saleGateway.save(any())).thenReturn(sale);

        Sale result = useCase.execute(2L, SaleStatus.CANCELADO);

        assertEquals(SaleStatus.CANCELADO, result.getStatus());
        verify(vehicleClient, never()).markVehicleAsSold(anyLong());
        verify(saleGateway).save(sale);
    }

    @Test
    public void testSaleNotFoundShouldThrowException() {
        when(saleGateway.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(3L, SaleStatus.CONCLUIDO));
        verify(saleGateway, never()).save(any());
        verify(vehicleClient, never()).markVehicleAsSold(anyLong());
    }
}
