package com.fiap.saleservice.application.usecase;

import com.fiap.saleservice.domain.entity.Sale;
import com.fiap.saleservice.infrastructure.client.PaymentClient;
import com.fiap.saleservice.infrastructure.client.VehicleClient;
import com.fiap.saleservice.infrastructure.client.dto.PaymentRequest;
import com.fiap.saleservice.infrastructure.client.dto.PaymentResponse;
import com.fiap.saleservice.infrastructure.client.response.VehicleResponse;
import com.fiap.saleservice.infrastructure.controller.dto.CreateSaleResponse;
import com.fiap.saleservice.infrastructure.gateway.SaleGateway;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSaleUseCaseTest {

    @Test
    void shouldCreateSaleSuccessfully() {
        // Implementação simples do SaleGateway em memória
        SaleGateway saleGateway = new InMemorySaleGateway();
        VehicleClient vehicleClient = new SimpleVehicleClient();
        PaymentClient paymentClient = new SimplePaymentClient();

        // Criando o caso de uso
        CreateSaleUseCase useCase = new CreateSaleUseCase(saleGateway, vehicleClient, paymentClient);

        // Executando o caso de uso
        CreateSaleResponse response = useCase.execute(1L, "João");

        // Verificações
        assertNotNull(response);
        assertEquals("João", saleGateway.findAll().get(0).getBuyer());
    }

    // Implementação simples do SaleGateway para testes
    static class InMemorySaleGateway implements SaleGateway {
        private final List<Sale> sales = new ArrayList<>();

        @Override
        public Sale save(Sale sale) {
            sales.add(sale);
            return sale;
        }

        @Override
        public void delete(Long saleId) {
            sales.removeIf(s -> s.getId().equals(saleId));
        }

        @Override
        public List<Sale> findAll() {
            return sales;
        }

        @Override
        public Optional<Sale> findById(Long id) {
            return sales.stream().filter(s -> s.getId().equals(id)).findFirst();
        }
    }

    // Implementação simples do VehicleClient para testes
    static class SimpleVehicleClient extends VehicleClient {

        public SimpleVehicleClient() {
            super("http://test");  // Passando um valor qualquer para a URL
        }

        @Override
        public VehicleResponse getVehicleById(Long vehicleId) {
            // Simulando a resposta para o veículo
            VehicleResponse vehicleResponse = new VehicleResponse();
            vehicleResponse.setId(vehicleId);
            vehicleResponse.setPreco(35000.0);
            vehicleResponse.setStatus("DISPONIVEL");
            return vehicleResponse;
        }
    }

    // Implementação simples do PaymentClient para testes
    static class SimplePaymentClient implements PaymentClient {
        @Override
        public PaymentResponse createPayment(PaymentRequest request) {
            // Simulando o pagamento
            return new PaymentResponse(1L, request.getSaleId(), request.getValue(), "APROVADO");
        }
    }
}
