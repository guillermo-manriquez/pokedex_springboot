package com.pokemon.coleccion_service.controller;

import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;
import com.pokemon.coleccion_service.service.ListaPokemonDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listas")
@Tag(name="API Lista Pokemon Detalle",description = "API para la gestion de pokemon dentro de listas")
public class ListaPokemonDetalleController {

    private final ListaPokemonDetalleService
            detalleService;

    public ListaPokemonDetalleController(

            ListaPokemonDetalleService detalleService

    ) {

        this.detalleService = detalleService;
    }

    // Agregar pokemon
    @Operation(summary ="Agregar pokemon a lista",description = "Endpoint permite agregar un pokemon a una lista")
    @ApiResponse(responseCode="201",description = "Pokemon agregado correctamente a la lista")
    @ApiResponse(responseCode="404",description = "Lista o pokemon no encontrado")
    @PostMapping("/{idLista}/pokemon/{idPokemon}")
    public ResponseEntity<ListaPokemonDetalle> agregarPokemon(
            @Parameter(description = "ID de la lista") @PathVariable Integer idLista,
            @Parameter(description = "ID del pokemon") @PathVariable Integer idPokemon ) {

        ListaPokemonDetalle detalle = new ListaPokemonDetalle();

        detalle.setIdLista(idLista);

        detalle.setIdPokemon(idPokemon);

        ListaPokemonDetalle agregado = detalleService.agregarPokemon(detalle);

        return new ResponseEntity<>(agregado, HttpStatus.CREATED);
    }

    // Eliminar pokemon
    @Operation(summary ="Eliminar pokemon de lista",description = "Endpoint permite eliminar un pokemon de una lista")
    @ApiResponse(responseCode="200",description = "Pokemon eliminado correctamente")
    @ApiResponse(responseCode="404",description = "Pokemon no encontrado en la lista")
    @DeleteMapping("/{id}/pokemon/{pokemonId}")
    public ResponseEntity<String> eliminarPokemon(
            @Parameter(description = "ID de la lista") @PathVariable Integer id,
            @Parameter(description = "ID del pokemon") @PathVariable Integer pokemonId) {

        List<ListaPokemonDetalle> detalles = detalleService.verPokemonDeLista(id);

        ListaPokemonDetalle encontrado = null;

        for (ListaPokemonDetalle detalle : detalles) {

            if (detalle.getIdPokemon().equals(pokemonId)) {
                encontrado = detalle;
                break;
            }
        }

        if (encontrado == null) {
            return new ResponseEntity<>("Pokemon no encontrado", HttpStatus.NOT_FOUND);
        }

        detalleService.eliminarPokemon(encontrado.getIdDetalle());

        return new ResponseEntity<>("Pokemon eliminado", HttpStatus.OK);
    }

    // Pokemon de lista
    @Operation(summary ="Obtener pokemon de una lista",description = "Endpoint permite consultar todos los pokemon asociados a una lista")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de pokemon")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/{idLista}/pokemon")
    public ResponseEntity<List<ListaPokemonDetalle>> verPokemonDeLista(@Parameter(description = "ID de la lista") @PathVariable Integer idLista) {

        List<ListaPokemonDetalle> pokemon = detalleService.verPokemonDeLista(idLista);

        if (pokemon.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }
}