package com.pokedex.notificacion_service.Service;

import com.pokedex.notificacion_service.Dto.NotificacionRequest;
import com.pokedex.notificacion_service.Dto.NotificacionResponse;
import com.pokedex.notificacion_service.Entity.Notificacion;
import com.pokedex.notificacion_service.Repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void createNotification_deberiaGuardarNotificacion() {

        // Arrange

        NotificacionRequest request = new NotificacionRequest();
        request.setUserId(1L);
        request.setMessage("Bienvenido");
        request.setType("WELCOME");

        // Act

        notificacionService.createNotification(request);

        // Assert

        verify(notificacionRepository)
                .save(any(Notificacion.class));
    }

    @Test
    void getMyNotifications_deberiaRetornarLista() {

        // Arrange

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setUserId(1L);
        notificacion.setMessage("Bienvenido");
        notificacion.setType("WELCOME");
        notificacion.setIsRead(false);

        when(notificacionRepository.findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(notificacion));

        // Act

        List<NotificacionResponse> response =
                notificacionService.getMyNotifications(1L);

        // Assert

        assertEquals(1, response.size());
        assertEquals("WELCOME", response.get(0).getType());
        assertFalse(response.get(0).getIsRead());

        verify(notificacionRepository)
                .findByUserIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void markAsRead_deberiaMarcarComoLeida() {

        // Arrange

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setIsRead(false);

        when(notificacionRepository.findById(1L))
                .thenReturn(Optional.of(notificacion));

        // Act

        notificacionService.markAsRead(1L);

        // Assert

        assertTrue(notificacion.getIsRead());

        verify(notificacionRepository)
                .save(notificacion);
    }

    @Test
    void markAsRead_deberiaLanzarExcepcionSiNoExiste() {

        // Arrange

        when(notificacionRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> notificacionService.markAsRead(1L)
        );

        assertEquals(
                "Notificacion no encontrada",
                exception.getMessage()
        );

        verify(notificacionRepository, never())
                .save(any());
    }
}