package com.pokemon.coleccion_service.controller;

import com.pokemon.coleccion_service.entity.ListaPokemon;
import com.pokemon.coleccion_service.service.ListaPokemonService;
import jakarta.validation.Valid;
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
@Tag(name="API Lista Pokemon",description = "API para la gestion de listas de pokemon")
public class ListaPokemonController {

    private final ListaPokemonService listaService;

    public ListaPokemonController(
            ListaPokemonService listaService
    ) {

        this.listaService = listaService;
    }

    // Crear lista
    @Operation(summary ="Crear lista",description = "Endpoint permite crear una nueva lista de pokemon")
    @ApiResponse(responseCode="201",description = "Lista creada correctamente")
    @ApiResponse(responseCode="400",description = "Datos invalidos para crear la lista")
    @PostMapping
    public ResponseEntity<ListaPokemon> crearLista( @Valid @RequestBody ListaPokemon listaPokemon) {

        ListaPokemon nuevaLista = listaService.crearLista(listaPokemon);

        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED
        );
    }

    // Listas por usuario
    @Operation(summary ="Obtener listas por usuario",description = "Endpoint permite consultar todas las listas asociadas a un usuario")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de listas")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ListaPokemon>> listarPorUsuario(@Parameter(description = "ID del usuario") @PathVariable Integer id) {

        List<ListaPokemon> listas = listaService.listarPorUsuario(id);

        if (listas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listas, HttpStatus.OK);
    }

    // Actualizar lista
    @Operation(summary ="Actualizar lista",description = "Endpoint permite modificar una lista existente")
    @ApiResponse(responseCode="200",description = "Lista actualizada correctamente")
    @ApiResponse(responseCode="404",description = "Lista no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ListaPokemon>
    actualizarLista(@Parameter(description = "ID de la lista") @PathVariable Integer id, @Valid @RequestBody ListaPokemon listaPokemon) {

        ListaPokemon actualizada = listaService.actualizarLista(id, listaPokemon);

        if (actualizada == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // Eliminar lista
    @Operation(summary ="Eliminar lista",description = "Endpoint permite eliminar una lista mediante su identificador")
    @ApiResponse(responseCode="200",description = "Lista eliminada correctamente")
    @ApiResponse(responseCode="404",description = "Lista no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLista(@Parameter(description = "ID de la lista") @PathVariable Integer id) {

        boolean eliminado = listaService.eliminarLista(id);

        if (!eliminado) {
            return new ResponseEntity<>("Lista no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Lista eliminada", HttpStatus.OK);
    }

    // Ver lista por id
    @Operation(summary ="Obtener lista por id",description = "Endpoint permite consultar una lista mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion de la lista")
    @ApiResponse(responseCode="404",description = "Lista no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ListaPokemon> obtenerListaPorId(@Parameter(description = "ID de la lista") @PathVariable Integer id) {

        ListaPokemon lista = listaService.obtenerPorId(id);

        if (lista == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}