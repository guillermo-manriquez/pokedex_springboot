package com.pokemon.pokemon_service.controller;

import com.pokemon.pokemon_service.entity.Pokemon;
import com.pokemon.pokemon_service.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@Tag(name="API Pokemon",description = "API para la gestion de pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Operation(summary ="Cargar pokemon en base de datos",description = "Endpoint permite cargar pokemon en la base de datos")
    @ApiResponse(responseCode="200",description = "Pokemon cargados correctamente")
    @PostMapping("/cargar")
    public ResponseEntity<String> cargarPokemonIniciales() {

        pokemonService.cargarPokemonIniciales();

        return ResponseEntity.ok(
                "Pokemon cargados correctamente"
        );
    }

    @Operation(summary ="Obtener pokemon por nombre",description = "Endpoint permite consultar un pokemon mediante su nombre")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del pokemon")
    @ApiResponse(responseCode="404",description = "Pokemon no encontrado")
    @GetMapping("/{nombre}")
    public ResponseEntity<Pokemon> obtenerPokemon(@Parameter(description = "Nombre del pokemon") @PathVariable String nombre) {
        Pokemon pokemon = pokemonService.obtenerPokemon(nombre);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(pokemon, HttpStatus.OK);
        }
    }

    @Operation(summary ="Obtener pokemon por id",description = "Endpoint permite consultar un pokemon mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del pokemon")
    @ApiResponse(responseCode="404",description = "Pokemon no encontrado")
    @GetMapping("/id/{id}")
    public ResponseEntity<Pokemon> obtenerPokemonPorId(@Parameter(description = "ID del pokemon") @PathVariable Integer id) {
        Pokemon pokemon = pokemonService.obtenerPokemonPorId(id);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(pokemon, HttpStatus.OK);
        }
    }
}