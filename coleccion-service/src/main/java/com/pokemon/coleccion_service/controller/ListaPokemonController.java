package com.pokemon.coleccion_service.controller;

import com.pokemon.coleccion_service.entity.ListaPokemon;

import com.pokemon.coleccion_service.service
        .ListaPokemonService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaPokemonController {

    private final ListaPokemonService listaService;

    public ListaPokemonController(
            ListaPokemonService listaService
    ) {

        this.listaService = listaService;
    }

    // Crear lista
    @PostMapping
    public ResponseEntity<ListaPokemon> crearLista( @Valid @RequestBody ListaPokemon listaPokemon) {

        ListaPokemon nuevaLista = listaService.crearLista(listaPokemon);

        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED
        );
    }


    // Listas por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ListaPokemon>> listarPorUsuario(@PathVariable Integer id) {

        List<ListaPokemon> listas = listaService.listarPorUsuario(id);

        if (listas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listas, HttpStatus.OK);
    }


    // Actualizar lista
    @PutMapping("/{id}")
    public ResponseEntity<ListaPokemon>
    actualizarLista(@PathVariable Integer id, @Valid @RequestBody ListaPokemon listaPokemon) {

        ListaPokemon actualizada = listaService.actualizarLista(id, listaPokemon);

        if (actualizada == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }


    // Eliminar lista
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLista(@PathVariable Integer id) {

        boolean eliminado = listaService.eliminarLista(id);

        if (!eliminado) {
            return new ResponseEntity<>("Lista no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Lista eliminada", HttpStatus.OK);
    }

    // Ver lista por id
    @GetMapping("/{id}")
    public ResponseEntity<ListaPokemon> obtenerListaPorId(@PathVariable Integer id) {

        ListaPokemon lista = listaService.obtenerPorId(id);

        if (lista == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}