package com.pokemon.coleccion_service.controller;

import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;
import com.pokemon.coleccion_service.service.ListaPokemonDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaPokemonDetalleController {

    private final ListaPokemonDetalleService
            detalleService;

    public ListaPokemonDetalleController(

            ListaPokemonDetalleService detalleService

    ) {

        this.detalleService = detalleService;
    }

    // Agregar pokemon
    @PostMapping("/{idLista}/pokemon/{idPokemon}")
    public ResponseEntity<ListaPokemonDetalle> agregarPokemon(@PathVariable Integer idLista, @PathVariable Integer idPokemon ) {

        ListaPokemonDetalle detalle = new ListaPokemonDetalle();

        detalle.setIdLista(idLista);

        detalle.setIdPokemon(idPokemon);

        ListaPokemonDetalle agregado = detalleService.agregarPokemon(detalle);

        return new ResponseEntity<>(agregado, HttpStatus.CREATED);
    }


    // Eliminar pokemon
    @DeleteMapping("/{id}/pokemon/{pokemonId}")
    public ResponseEntity<String> eliminarPokemon(@PathVariable Integer id, @PathVariable Integer pokemonId) {

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
    @GetMapping("/{idLista}/pokemon")
    public ResponseEntity<List<ListaPokemonDetalle>> verPokemonDeLista(@PathVariable Integer idLista) {

        List<ListaPokemonDetalle> pokemon = detalleService.verPokemonDeLista(idLista);

        if (pokemon.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }
}