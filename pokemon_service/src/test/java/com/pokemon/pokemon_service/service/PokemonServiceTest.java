package com.pokemon.pokemon_service.service;

import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.repository.PokedexRepository;
import com.pokemon.pokemon_service.service.PokedexService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class PokemonServiceTest {
    @Mock
    PokedexRepository pokedexRepository;

    @InjectMocks
    PokedexService serviciopdex;

    Pokedex pokedex;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        pokedex = new Pokedex();
        pokedex.setIdPokedex(1);

    }

    @Test
    @DisplayName("Verificar cantidad de Pokedex")
    void checkCantidadPokedex() {

        List<Pokedex> lista =
                Arrays.asList(
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex(),
                        new Pokedex()
                );

        when(pokedexRepository.findAll()).thenReturn(lista);

        int cantidad = serviciopdex.obtenerPokedex().size();

        log.info("Cantidad de Pokedex {}", cantidad);

        assertEquals(10, cantidad);

        verify(pokedexRepository).findAll();

    }

    @Test
    @DisplayName("Verificar obtener Pokedex por ID")
    void checkPokedexPorId(){
        when(pokedexRepository.findById(1)).thenReturn(Optional.of(pokedex));

        Pokedex resultado = serviciopdex.obtenerPorId(1);

        log.info("Pokedex encontrada {}", resultado.getIdPokedex());

        assertNotNull(resultado);

        assertEquals(1, resultado.getIdPokedex());

        verify(pokedexRepository).findById(1);

    }

    @Test
    @DisplayName("Verificar Pokedex inexistente")
    void checkPokedexNoExiste(){

        when(pokedexRepository.findById(-1)).thenReturn(Optional.empty());

        Pokedex resultado = serviciopdex.obtenerPorId(-1);

        log.info("Resultado Pokedex inexistente {}", resultado);

        assertNull(resultado);

        verify(pokedexRepository).findById(-1);

    }

}

