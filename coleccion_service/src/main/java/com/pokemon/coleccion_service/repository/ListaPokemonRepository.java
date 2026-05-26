package com.pokemon.coleccion_service.repository;

import com.pokemon.coleccion_service.entity.ListaPokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaPokemonRepository
        extends JpaRepository<ListaPokemon, Integer> {

    List<ListaPokemon> findByIdUsuario(
            Integer idUsuario
    );
}