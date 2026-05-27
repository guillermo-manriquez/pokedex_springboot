package com.pokemon.coleccion_service.service;

import com.pokemon.coleccion_service.client.PokemonClient;
import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;
import com.pokemon.coleccion_service.repository.ListaPokemonDetalleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaPokemonDetalleService {

    private final PokemonClient pokemonClient;
    private final ListaPokemonDetalleRepository detalleRepository;

    public ListaPokemonDetalleService(
            ListaPokemonDetalleRepository detalleRepository,
            PokemonClient pokemonClient
    ) {
        this.detalleRepository = detalleRepository;
        this.pokemonClient = pokemonClient;
    }

    // AGREGAR POKEMON
    public ListaPokemonDetalle agregarPokemon(ListaPokemonDetalle detalle) {

        detalle.setFechaAgregado(LocalDateTime.now());

        pokemonClient.obtenerPokemon(detalle.getIdPokemon());

        return detalleRepository.save(detalle);
    }

    // =========================
    // VER POKEMON DE LISTA
    // =========================
    public List<ListaPokemonDetalle>
    verPokemonDeLista(Integer idLista) {

        return detalleRepository.findByIdLista(idLista);
    }

    // =========================
    // ELIMINAR POKEMON
    // =========================
    public boolean eliminarPokemon(Integer idDetalle) {

        ListaPokemonDetalle detalle = detalleRepository.findById(idDetalle).orElse(null);

        if (detalle == null) {
            return false;
        }

        detalleRepository.delete(detalle);

        return true;
    }
}
