package com.pokemon.carrito_service.client;

import com.pokemon.carrito_service.dto.ProductoCarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service")
public interface ProductoClient {

    @GetMapping("/{id}")
    ProductoCarritoDTO obtenerProducto(
            @PathVariable Integer id);
}