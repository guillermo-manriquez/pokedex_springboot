
package com.pokemon.coleccion_service.client;

import com.pokemon.coleccion_service.dto.PokemonCollectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pokemon-service")
public interface PokemonClient {

    @GetMapping("/pokemon/id/{id}")
    PokemonCollectionDTO obtenerPokemon(
            @PathVariable Integer id
    );
}