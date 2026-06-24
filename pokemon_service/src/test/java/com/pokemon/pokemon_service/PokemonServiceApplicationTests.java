package com.pokemon.pokemon_service;

import com.pokemon.pokemon_service.entity.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.pokemon.pokemon_service.service.PokemonService;
import com.pokemon.pokemon_service.service.PokedexService;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class PokemonServiceApplicationTests {

	@Autowired
	PokemonService serviciopkm;

	@Autowired
	PokedexService serviciopdex;

	@Test
	@DisplayName("Verificar cantidad de Pokedex")
	void checkCantidadPokedex() {

		int cantidad = serviciopdex.obtenerPokedex().size();

		log.info("Verificando cantidad de Pokedex");

		assertEquals(10, serviciopdex.obtenerPokedex().size());
	}

	@Test
	@DisplayName("Verificar que nombre no es Null")
	void checkPokemonNombreNoNulo() {

		Pokemon pokemon = serviciopkm.obtenerPokemon("bulbasaur");

		log.info("Verificando nombre de {}", pokemon.getNombre());

		assertNotNull(pokemon.getNombre());
	}

	@Test
	@DisplayName("Verificar nombre de Pokemon por ID")
	void checkPokemonNombre() {

		Pokemon pokemon = serviciopkm.obtenerPokemonPorId(1);

		log.info("Verificando pokemon {}", pokemon.getNombre());

		assertEquals("bulbasaur", pokemon.getNombre());
	}
}