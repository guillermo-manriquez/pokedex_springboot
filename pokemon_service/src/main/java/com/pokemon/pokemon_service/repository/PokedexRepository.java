package com.pokemon.pokemon_service.repository;

import com.pokemon.pokemon_service.entity.Pokedex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokedexRepository extends JpaRepository<Pokedex, Integer> {
}