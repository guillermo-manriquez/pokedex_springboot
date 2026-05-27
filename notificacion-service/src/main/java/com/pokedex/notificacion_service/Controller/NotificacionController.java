package com.pokedex.notificacion_service.Controller;


import com.pokedex.notificacion_service.Dto.NotificacionRequest;
import com.pokedex.notificacion_service.Dto.NotificacionResponse;
import com.pokedex.notificacion_service.Service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping("/internal/create")
    public ResponseEntity<Void> createNotification(@RequestBody NotificacionRequest request) {
        notificacionService.createNotification(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/{userId}")
    public ResponseEntity<List<NotificacionResponse>> getMyNotifications(@PathVariable Integer userId) {
        System.out.println("CONTROLLER HIT - userId: " + userId);
        return ResponseEntity.ok(notificacionService.getMyNotifications(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificacionService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("TEST HIT");
        return ResponseEntity.ok("notificacion-service is alive");
    }
}
