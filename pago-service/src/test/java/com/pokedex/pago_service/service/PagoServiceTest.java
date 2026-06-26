package com.pokedex.pago_service.service;

import com.pokedex.pago_service.Dto.OrdenDTO;
import com.pokedex.pago_service.Dto.PagoRequest;
import com.pokedex.pago_service.Dto.PagoResponse;
import com.pokedex.pago_service.Model.Pago;
import com.pokedex.pago_service.Repository.PagoRepository;
import com.pokedex.pago_service.Service.PagoService;
import com.pokedex.pago_service.client.NotificacionRequest;
import com.pokedex.pago_service.client.NotificacionServiceClient;
import com.pokedex.pago_service.client.OrdenClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private NotificacionServiceClient notificacionServiceClient;

    @Mock
    private OrdenClient ordenClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void processPayment_deberiaCompletarPagoCorrectamente() {

        // Arrange

        PagoRequest request = new PagoRequest();
        request.setOrderId(1);
        request.setUserId(1L);
        request.setAmount(new BigDecimal("10000"));
        request.setCurrency("CLP");
        request.setPaymentMethod(Pago.PaymentMethod.CREDIT_CARD);
        request.setCardNumber("123456789");

        OrdenDTO orden = new OrdenDTO();
        orden.setIdOrden(1);
        orden.setIdUsuario(1L);

        when(ordenClient.getOrdenById(1))
                .thenReturn(orden);

        // Act

        PagoResponse response = pagoService.processPayment(request);

        // Assert

        assertEquals("COMPLETED", response.getStatus());
        assertEquals("Pago procesado exitosamente", response.getMessage());

        verify(ordenClient).getOrdenById(1);
        verify(pagoRepository, times(2)).save(any(Pago.class));
        verify(notificacionServiceClient)
                .createNotificacion(any(NotificacionRequest.class));
    }

    @Test
    void processPayment_deberiaLanzarExcepcionSiOrdenNoExiste() {

        // Arrange

        PagoRequest request = new PagoRequest();
        request.setOrderId(1);
        request.setUserId(1L);
        request.setAmount(new BigDecimal("10000"));
        request.setPaymentMethod(Pago.PaymentMethod.CREDIT_CARD);

        when(ordenClient.getOrdenById(1))
                .thenReturn(null);

        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pagoService.processPayment(request)
        );

        assertTrue(exception.getMessage().contains("Orden no existe"));

        verify(ordenClient).getOrdenById(1);
        verify(pagoRepository, never()).save(any());
    }

    @Test
    void processPayment_deberiaLanzarExcepcionSiUsuarioNoCoincide() {

        // Arrange

        PagoRequest request = new PagoRequest();
        request.setOrderId(1);
        request.setUserId(1L);
        request.setAmount(new BigDecimal("10000"));
        request.setPaymentMethod(Pago.PaymentMethod.CREDIT_CARD);

        OrdenDTO orden = new OrdenDTO();
        orden.setIdOrden(1);
        orden.setIdUsuario(2L);

        when(ordenClient.getOrdenById(1))
                .thenReturn(orden);

        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pagoService.processPayment(request)
        );

        assertTrue(exception.getMessage().contains("Usuario no corresponde"));

        verify(ordenClient).getOrdenById(1);
        verify(pagoRepository, never()).save(any());
    }

    @Test
    void getPaymentByOrderId_deberiaRetornarPago() {

        // Arrange

        Pago pago = new Pago();
        pago.setId(1L);
        pago.setOrderId(1);
        pago.setAmount(new BigDecimal("10000"));
        pago.setStatus(Pago.PaymentStatus.COMPLETED);

        when(pagoRepository.findByOrderId(1))
                .thenReturn(Optional.of(pago));

        // Act

        PagoResponse response = pagoService.getPaymentByOrderId(1);

        // Assert

        assertEquals(1L, response.getPaymentId());
        assertEquals("COMPLETED", response.getStatus());

        verify(pagoRepository).findByOrderId(1);
    }
}
