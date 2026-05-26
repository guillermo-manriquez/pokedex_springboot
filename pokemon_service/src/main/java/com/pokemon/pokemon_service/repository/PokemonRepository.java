package com.pokemon.pokemon_service.repository;

import com.pokemon.pokemon_service.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}