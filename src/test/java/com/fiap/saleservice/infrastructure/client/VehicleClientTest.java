package com.fiap.saleservice.infrastructure.client;

import com.fiap.saleservice.exception.NotFoundException;
import com.fiap.saleservice.infrastructure.client.response.VehicleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleClientTest {

    private RestTemplate restTemplate;
    private VehicleClient vehicleClient;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        vehicleClient = new VehicleClient("http://localhost:8080");
        ReflectionTestUtils.setField(vehicleClient, "restTemplate", restTemplate);
    }

    @Test
    void shouldReturnVehicleResponseWhenFound() {
        VehicleResponse mockResponse = new VehicleResponse();
        mockResponse.setId(1L);
        mockResponse.setMarca("Fiat");

        when(restTemplate.getForObject("http://localhost:8080/vehicles/1", VehicleResponse.class))
                .thenReturn(mockResponse);

        VehicleResponse response = vehicleClient.getVehicleById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Fiat", response.getMarca());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenVehicleIsNotFound() {
        when(restTemplate.getForObject("http://localhost:8080/vehicles/999", VehicleResponse.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(NotFoundException.class, () -> vehicleClient.getVehicleById(999L));
    }

    @Test
    void shouldCallPutToMarkVehicleAsSold() {
        vehicleClient.markVehicleAsSold(5L);

        verify(restTemplate).put("http://localhost:8080/vehicles/5/sell", null);
    }
}
