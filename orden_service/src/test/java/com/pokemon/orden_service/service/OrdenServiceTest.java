package com.pokemon.orden_service.service;

import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Repository.OrdenRepository;
import com.pokemon.orden_service.Service.OrdenService;
import com.pokemon.orden_service.client.UserClient;
import com.pokemon.orden_service.client.CarritoClient;
import com.pokemon.orden_service.client.PagoServiceClient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class OrdenServiceTest {

    @Mock
    OrdenRepository ordenRepository;

    @Mock
    UserClient userClient;

    @Mock
    CarritoClient carritoClient;

    @Mock
    PagoServiceClient pagoServiceClient;

    @InjectMocks
    OrdenService servicio;

    Orden orden;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        orden = new Orden();
        orden.setIdOrden(1);
        orden.setIdUsuario(1L);
        orden.setIdCarrito(1);
        orden.setEstadoOrden("PAGADO");
        orden.setTotalMonto(new BigDecimal(15000.0));

    }

    @Test
    @DisplayName("Verificar que la orden existe")
    void checkOrdenExiste() {

        when(ordenRepository.findById(1)).thenReturn(Optional.of(orden));

        Orden resultado = servicio.obtenerPorId(1);

        log.info("Verificando existencia de orden");

        assertNotNull(resultado);

        verify(ordenRepository).findById(1);

    }

    @Test
    @DisplayName("Verificar que la orden tiene usuario asociado")
    void checkOrdenUsuario() {

        when(ordenRepository.findById(1)).thenReturn(Optional.of(orden));

        Orden resultado = servicio.obtenerPorId(1);

        log.info("Verificando usuario de la orden {}", resultado.getIdOrden());

        assertNotNull(resultado.getIdUsuario());

        verify(ordenRepository).findById(1);
    }

    @Test
    @DisplayName("Verificar estado de la orden")
    void checkEstadoOrden() {

        when(ordenRepository.findById(1)).thenReturn(Optional.of(orden));

        Orden resultado = servicio.obtenerPorId(1);

        log.info("Verificando estado de la orden");

        assertEquals("PAGADO", resultado.getEstadoOrden());

        verify(ordenRepository).findById(1);
    }


}

