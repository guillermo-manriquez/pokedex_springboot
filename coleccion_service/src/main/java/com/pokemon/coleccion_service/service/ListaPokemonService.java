package com.pokemon.coleccion_service.service;

import com.pokemon.coleccion_service.client.UserClient;
import com.pokemon.coleccion_service.entity.ListaPokemon;
import com.pokemon.coleccion_service.repository.ListaPokemonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaPokemonService {

    private final UserClient userClient;
    private final ListaPokemonRepository listaPokemonRepository;

    public ListaPokemonService(
            ListaPokemonRepository listaPokemonRepository,
            UserClient userClient
    ) {

        this.listaPokemonRepository = listaPokemonRepository;
        this.userClient = userClient;
    }

    // Crear lista
    public ListaPokemon crearLista(ListaPokemon listaPokemon) {

        listaPokemon.setFechaCreacion(LocalDateTime.now());

        userClient.obtenerUsuario(listaPokemon.getIdUsuario().longValue());

        return listaPokemonRepository.save(listaPokemon);
    }

    // Listar por usuario
    public List<ListaPokemon> listarPorUsuario(Integer idUsuario) {

        return listaPokemonRepository.findByIdUsuario(idUsuario);
    }


    // Actualizar lista
    public ListaPokemon actualizarLista(Integer id, ListaPokemon nuevaLista) {
        ListaPokemon lista = listaPokemonRepository.findById(id).orElse(null);

        if (lista == null) {
            return null;
        }

        lista.setNombreLista(nuevaLista.getNombreLista());

        lista.setDescripcion(nuevaLista.getDescripcion());

        return listaPokemonRepository.save(lista);
    }


    // Eliminar listas
    public boolean eliminarLista(Integer id) {

        ListaPokemon lista = listaPokemonRepository.findById(id).orElse(null);

        if (lista == null) {
            return false;
        }

        listaPokemonRepository.delete(lista);
        return true;
    }

    // Listar por id
    public ListaPokemon obtenerPorId(Integer id) {

        return listaPokemonRepository.findById(id).orElse(null);
    }
}