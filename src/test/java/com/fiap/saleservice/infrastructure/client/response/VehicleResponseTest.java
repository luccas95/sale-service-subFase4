package com.fiap.saleservice.infrastructure.client.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleResponseTest {

    @Test
    void shouldSetAndGetAllFields() {
        VehicleResponse vehicle = new VehicleResponse();

        vehicle.setId(1L);
        vehicle.setMarca("Fiat");
        vehicle.setModelo("Uno");
        vehicle.setAno(2020);
        vehicle.setCor("Prata");
        vehicle.setPreco(35000.0);
        vehicle.setStatus("DISPONIVEL");

        assertEquals(1L, vehicle.getId());
        assertEquals("Fiat", vehicle.getMarca());
        assertEquals("Uno", vehicle.getModelo());
        assertEquals(2020, vehicle.getAno());
        assertEquals("Prata", vehicle.getCor());
        assertEquals(35000.0, vehicle.getPreco());
        assertEquals("DISPONIVEL", vehicle.getStatus());
    }
}
