package com.pokedex.notificacion_service.Controller;

import com.pokedex.notificacion_service.Dto.NotificacionRequest;
import com.pokedex.notificacion_service.Dto.NotificacionResponse;
import com.pokedex.notificacion_service.Service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("api/v1/notificaciones")
@RequiredArgsConstructor
@Tag(name="API Notificacion",description = "API para la gestion de notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Operation(summary ="Crear notificacion",description = "Endpoint permite crear una nueva notificacion")
    @ApiResponse(responseCode="200",description = "Notificacion creada correctamente")
    @PostMapping("/internal/create")
    public ResponseEntity<Void> createNotification(@RequestBody NotificacionRequest request) {
        notificacionService.createNotification(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary ="Obtener mis notificaciones",description = "Endpoint permite consultar todas las notificaciones de un usuario")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de notificaciones")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/me/{userId}")
    public ResponseEntity<List<NotificacionResponse>> getMyNotifications(@Parameter(description = "ID del usuario") @PathVariable Integer userId) {
        System.out.println("CONTROLLER HIT - userId: " + userId);
        return ResponseEntity.ok(notificacionService.getMyNotifications(userId));
    }

    @Operation(summary ="Marcar notificacion como leida",description = "Endpoint permite marcar una notificacion como leida")
    @ApiResponse(responseCode="200",description = "Notificacion marcada como leida correctamente")
    @ApiResponse(responseCode="404",description = "Notificacion no encontrada")
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@Parameter(description = "ID de la notificacion") @PathVariable Long id) {
        notificacionService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary ="Probar servicio de notificaciones",description = "Endpoint permite verificar que el servicio de notificaciones se encuentra operativo")
    @ApiResponse(responseCode="200",description = "Servicio operativo correctamente")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("TEST HIT");
        return ResponseEntity.ok("notificacion-service is alive");
    }
}