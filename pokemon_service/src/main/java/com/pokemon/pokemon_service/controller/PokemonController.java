package com.pokemon.pokemon_service.controller;

import com.pokemon.pokemon_service.entity.Pokemon;
import com.pokemon.pokemon_service.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    //@GetMapping("/{nombre}")
    //public ResponseEntity<Pokemon> obtenerPokemon(@PathVariable String nombre) {
    //
    //    try {
    //        Pokemon pokemon = pokemonService.obtenerPokemon(nombre);
    //        return ResponseEntity.ok(pokemon);
    //
    //    } catch (Exception e) {
    //        return ResponseEntity
    //                .badRequest()
    //                .build();
    //    }
    //}

    @GetMapping("/{nombre}")
    public ResponseEntity<Pokemon> obtenerPokemon(@PathVariable String nombre) {
        Pokemon pokemon = pokemonService.obtenerPokemon(nombre);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(pokemon, HttpStatus.OK);
        }
    }

    //@GetMapping("/{id}")
    //public ResponseEntity<Solicitud> getsolID(@PathVariable int id){
    //    Solicitud i = service.buscarPorID(id);
    //    if (i==null){
    //        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //    }
    //    else{
    //        return new ResponseEntity<>(i, HttpStatus.OK);
    //    }
    //}

    @GetMapping("/id/{id}")
    public ResponseEntity<Pokemon> obtenerPokemonPorId(@PathVariable Integer id) {
        Pokemon pokemon = pokemonService.obtenerPokemonPorId(id);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(pokemon, HttpStatus.OK);
        }
    }

    //@GetMapping("/id/{id}")
    //public ResponseEntity<Pokemon> obtenerPokemonPorId(@PathVariable Integer id) {
    //
    //    try {
    //        Pokemon pokemon = pokemonService.obtenerPokemonPorId(id);
    //        return ResponseEntity.ok(pokemon);
    //
    //    } catch (RuntimeException e) {
    //        return ResponseEntity.notFound().build();
    //    }
    //}
}