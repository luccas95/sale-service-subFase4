package com.fiap.saleservice.application.usecase;

import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.infrastructure.gateway.SaleGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListSalesUseCaseTest {

    private SaleGateway saleGateway;
    private ListSalesUseCase listSalesUseCase;

    @BeforeEach
    void setUp() {
        // Criando um mock do SaleGateway
        saleGateway = mock(SaleGateway.class);

        // Instanciando o caso de uso
        listSalesUseCase = new ListSalesUseCase(saleGateway);
    }

    @Test
    void shouldReturnListOfSales() {
        // Dados de exemplo
        Sale sale1 = new Sale(1L, 50000.0, "João", null);
        Sale sale2 = new Sale(2L, 30000.0, "Maria", null);

        // Simulando o comportamento do SaleGateway para retornar as vendas
        when(saleGateway.findAll()).thenReturn(List.of(sale1, sale2));

        // Executando o caso de uso
        List<Sale> sales = listSalesUseCase.execute();

        // Verificando o tamanho da lista
        assertNotNull(sales);
        assertEquals(2, sales.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoSales() {
        // Simulando o comportamento do SaleGateway para retornar uma lista vazia
        when(saleGateway.findAll()).thenReturn(List.of());

        // Executando o caso de uso
        List<Sale> sales = listSalesUseCase.execute();

        // Verificando se a lista está vazia
        assertNotNull(sales);
        assertTrue(sales.isEmpty());
    }
}
