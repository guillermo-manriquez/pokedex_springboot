package com.pokemon.coleccion_service.repository;

import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaPokemonDetalleRepository
        extends JpaRepository<
        ListaPokemonDetalle,
        Integer
        > {

    List<ListaPokemonDetalle>
    findByIdLista(Integer idLista);
}
