package com.pokemon.coleccion_service.service;

import com.pokemon.coleccion_service.client.PokemonClient;
import com.pokemon.coleccion_service.client.UserClient;
import com.pokemon.coleccion_service.entity.ListaPokemon;
import com.pokemon.coleccion_service.entity.ListaPokemonDetalle;
import com.pokemon.coleccion_service.repository.ListaPokemonDetalleRepository;
import com.pokemon.coleccion_service.repository.ListaPokemonRepository;
import com.pokemon.coleccion_service.service.ListaPokemonDetalleService;
import com.pokemon.coleccion_service.service.ListaPokemonService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class ColeccionServiceTest {

    @Mock
    ListaPokemonRepository listaRepository;

    @Mock
    ListaPokemonDetalleRepository detalleRepository;

    @Mock
    UserClient userClient;

    @Mock
    PokemonClient pokemonClient;

    @InjectMocks
    ListaPokemonService servicioLista;

    @InjectMocks
    ListaPokemonDetalleService servicioDetalle;

    ListaPokemon lista;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        lista = new ListaPokemon();
        lista.setIdLista(1);
        lista.setIdUsuario(1);
        lista.setNombreLista("Favoritos");
        lista.setDescripcion("Pokemons favoritos");
        lista.setFechaCreacion(LocalDateTime.of(2025,1,1,0,0));
    }

    @Test
    @DisplayName("Verificar que existe una lista de Pokemon")
    void checkListaExiste() {

        when(listaRepository.findById(1)).thenReturn(Optional.of(lista));

        ListaPokemon resultado = servicioLista.obtenerPorId(1);

        log.info("Verificando lista {}", resultado.getNombreLista());

        assertNotNull(resultado);

        verify(listaRepository).findById(1);
    }

    @Test
    @DisplayName("Verificar eliminacion de un Pokemon inexistente")
    void checkEliminarPokemonInexistente() {

        when(detalleRepository.findById(-1)).thenReturn(Optional.empty());

        boolean eliminado = servicioDetalle.eliminarPokemon(-1);

        log.info("Verificando eliminacion de Pokemon inexistente");

        assertFalse(eliminado);

        verify(detalleRepository).findById(-1);
        verify(detalleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Verificar actualizacion del nombre de la lista")
    void checkActualizarNombreLista() {

        when(listaRepository.findById(1)).thenReturn(Optional.of(lista));

        when(listaRepository.save(any(ListaPokemon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String nombreAnterior = lista.getNombreLista();

        lista.setNombreLista("Equipo Competitivo");

        ListaPokemon actualizada = servicioLista.actualizarLista(1, lista);

        log.info("Actualizando nombre de lista");

        assertNotEquals(nombreAnterior, actualizada.getNombreLista());
        assertEquals("Equipo Competitivo", actualizada.getNombreLista());

        verify(listaRepository).findById(1);
        verify(listaRepository).save(any(ListaPokemon.class));
    }
}
