package com.pokemon.orden_service.client;

import com.pokemon.orden_service.dto.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carrito-service")
public interface CarritoClient {

    @GetMapping("/carritos/{id}")
    CarritoDTO obtenerCarrito(@PathVariable Long id);
}


