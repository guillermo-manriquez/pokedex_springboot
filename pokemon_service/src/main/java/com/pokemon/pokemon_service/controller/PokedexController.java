package com.pokemon.pokemon_service.controller;

import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.service.PokedexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon/pokedex")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    //@PostMapping("/init")
    //public ResponseEntity<String> init() {
    //
    //    pokedexService.inicializarPokedex();
    //
    //    return ResponseEntity.ok("Pokedex inicializada");
    //}

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

    @GetMapping("/id/{id}")
    public ResponseEntity<Pokedex> obtenerPorId(@PathVariable Integer id) {
        Pokedex pokedex = pokedexService.obtenerPorId(id);

        if (pokedex == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {
            return new ResponseEntity<>(pokedex, HttpStatus.OK);
        }
    }
}