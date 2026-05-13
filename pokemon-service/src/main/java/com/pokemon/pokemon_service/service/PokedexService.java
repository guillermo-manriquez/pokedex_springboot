package com.pokemon.pokemon_service.service;

import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.repository.PokedexRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokedexService {

    private final PokedexRepository pokedexRepository;

    public PokedexService(PokedexRepository pokedexRepository) {
        this.pokedexRepository = pokedexRepository;
    }

    //Obtener todas
    public List<Pokedex> obtenerPokedex() {
        return pokedexRepository.findAll();
    }

    // Obtener por id
    public Pokedex obtenerPorId(Integer id) {
        return pokedexRepository.findById(id).orElse(null);
    }
}