package com.fiap.saleservice.infrastructure.gateway;

import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.infrastructure.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleRepositoryGatewayTest {

    private SaleRepository repository;
    private SaleRepositoryGateway gateway;

    @BeforeEach
    void setUp() {
        repository = mock(SaleRepository.class);
        gateway = new SaleRepositoryGateway(repository);
    }

    @Test
    void shouldSaveSaleAndReturnIt() {
        Sale sale = new Sale();
        when(repository.save(sale)).thenReturn(sale);

        Sale result = gateway.save(sale);

        assertEquals(sale, result);
        verify(repository).save(sale);
    }

    @Test
    void shouldReturnListOfSales() {
        List<Sale> sales = Arrays.asList(new Sale(), new Sale());
        when(repository.findAll()).thenReturn(sales);

        List<Sale> result = gateway.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void shouldReturnSaleById() {
        Sale sale = new Sale();
        when(repository.findById(1L)).thenReturn(Optional.of(sale));

        Optional<Sale> result = gateway.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(sale, result.get());
        verify(repository).findById(1L);
    }

    @Test
    void shouldCallDeleteById() {
        gateway.delete(1L);

        verify(repository).deleteById(1L);
    }
}
