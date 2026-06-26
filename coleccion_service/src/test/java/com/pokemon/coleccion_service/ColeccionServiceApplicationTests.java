package com.pokemon.coleccion_service;

import com.pokemon.coleccion_service.entity.ListaPokemon;
import com.pokemon.coleccion_service.service.ListaPokemonDetalleService;
import com.pokemon.coleccion_service.service.ListaPokemonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ColeccionServiceApplicationTests {

	@Autowired
	ListaPokemonService servicioLista;

	@Autowired
	ListaPokemonDetalleService servicioDetalle;

	/*
	Requiere insertar en bd http://localhost/phpmyadmin/

	INSERT INTO lista_pokemon
			(
					id_lista,
					id_usuario,
					nombre_lista,
					descripcion,
					fecha_creacion
					)
	VALUES
			(
    1,
			1,
			'Favoritos',
			'Pokemons favoritos',
			'2025-01-01 00:00:00'
			);
	*/

	@Test
	@DisplayName("Verificar que existe una lista de Pokemon")
	void checkListaExiste() {

		ListaPokemon lista = servicioLista.obtenerPorId(1);

		log.info("Verificando lista {}", lista.getNombreLista());

		assertNotNull(lista);
	}


	@Test
	@DisplayName("Verificar eliminacion de un Pokemon inexistente")
	void checkEliminarPokemonInexistente() {

		boolean eliminado = servicioDetalle.eliminarPokemon(-1);

		log.info("Verificando eliminacion de Pokemon inexistente");

		assertFalse(eliminado);
	}


	@Test
	@DisplayName("Verificar actualizacion del nombre de la lista")
	void checkActualizarNombreLista() {

		ListaPokemon lista = servicioLista.obtenerPorId(1);

		String nombreAnterior = lista.getNombreLista();

		lista.setNombreLista("Equipo Competitivo");

		ListaPokemon actualizada = servicioLista.actualizarLista(1, lista);

		log.info("Actualizando nombre de lista");

		assertNotEquals(nombreAnterior, actualizada.getNombreLista());
		assertEquals("Equipo Competitivo", actualizada.getNombreLista());
	}

}