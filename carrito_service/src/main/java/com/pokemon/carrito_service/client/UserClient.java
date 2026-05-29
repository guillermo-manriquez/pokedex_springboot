package com.pokemon.carrito_service.client;

import com.pokemon.carrito_service.config.FeignConfig;
import com.pokemon.carrito_service.dto.UserCarritoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserCarritoDTO obtenerUsuario(@PathVariable Long id);
}