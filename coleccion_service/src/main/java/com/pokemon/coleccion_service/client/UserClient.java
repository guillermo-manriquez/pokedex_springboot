package com.pokemon.coleccion_service.client;

import com.pokemon.coleccion_service.dto.UserCollectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserCollectionDTO obtenerUsuario(@PathVariable Long id);
}