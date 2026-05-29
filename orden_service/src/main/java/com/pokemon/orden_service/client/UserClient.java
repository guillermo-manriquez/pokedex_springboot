package com.pokemon.orden_service.client;

import com.pokemon.orden_service.config.FeignConfig;
import com.pokemon.orden_service.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserDTO obtenerUsuario(@PathVariable Long id);
}