package com.pokemon.producto_service.client;

import com.pokemon.producto_service.dto.PkmProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pokemon-service")
public interface PkmClient {

    @GetMapping("/pokemon/id/{id}")
    PkmProductoDTO obtenerPokemon(
            @PathVariable Integer id
    );
}