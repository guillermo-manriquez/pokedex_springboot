package com.pokemon.coleccion_service.service;

import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;

import com.pokemon.coleccion_service.repository
        .ListaPokemonDetalleRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaPokemonDetalleService {

    private final ListaPokemonDetalleRepository
            detalleRepository;

    public ListaPokemonDetalleService(
            ListaPokemonDetalleRepository detalleRepository
    ) {

        this.detalleRepository = detalleRepository;
    }

    // =========================
    // AGREGAR POKEMON
    // =========================
    public ListaPokemonDetalle agregarPokemon(
            ListaPokemonDetalle detalle
    ) {

        detalle.setFechaAgregado(
                LocalDateTime.now()
        );

        return detalleRepository.save(detalle);
    }

    // =========================
    // VER POKEMON DE LISTA
    // =========================
    public List<ListaPokemonDetalle>
    verPokemonDeLista(Integer idLista) {

        return detalleRepository
                .findByIdLista(idLista);
    }

    // =========================
    // ELIMINAR POKEMON
    // =========================
    public boolean eliminarPokemon(
            Integer idDetalle
    ) {

        ListaPokemonDetalle detalle =
                detalleRepository
                        .findById(idDetalle)
                        .orElse(null);

        if (detalle == null) {
            return false;
        }

        detalleRepository.delete(detalle);

        return true;
    }
}
