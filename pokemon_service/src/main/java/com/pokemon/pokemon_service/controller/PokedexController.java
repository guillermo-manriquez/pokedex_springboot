package com.pokemon.pokemon_service.controller;

import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.service.PokedexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokedex")
@Tag(name="API Pokedex",description = "API para la gestion de la pokedex")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @Operation(summary ="Obtener todas las pokedex",description = "Endpoint permite consultar todos los registros de pokedex")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de pokedex")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping
    public ResponseEntity<List<Pokedex>> obtenerTodas() {

        List<Pokedex> pokedex =
                pokedexService.obtenerPokedex();

        if (pokedex.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            return new ResponseEntity<>(pokedex, HttpStatus.OK);
        }
    }

    @Operation(summary ="Obtener pokedex por id",description = "Endpoint permite consultar un pokedex mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion de la pokedex")
    @ApiResponse(responseCode="404",description = "Pokedex no encontrada")
    @GetMapping("/id/{id}")
    public ResponseEntity<Pokedex> obtenerPorId(@Parameter(description = "ID de pokedex") @PathVariable Integer id) {
        Pokedex pokedex = pokedexService.obtenerPorId(id);

        if (pokedex == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {
            return new ResponseEntity<>(pokedex, HttpStatus.OK);
        }
    }
}