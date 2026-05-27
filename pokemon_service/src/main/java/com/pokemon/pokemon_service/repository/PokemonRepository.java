package com.pokemon.pokemon_service.repository;

import com.pokemon.pokemon_service.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    List<Pokemon> findByTipo1(String tipo1);
}