package com.pokedex.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "notificacion-service")
public interface NotificacionServiceClient {

    @PostMapping("api/v1/notificaciones/internal/create")
    void createNotificacion(@RequestBody NotificacionRequest request);
}
