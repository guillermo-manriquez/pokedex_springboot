package com.pokemon.carrito_service;

import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Service.CarritoItemService;
import com.pokemon.carrito_service.Service.CarritoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CarritoServiceApplicationTests {

	@Autowired
	CarritoService servicioCarrito;

	@Autowired
	CarritoItemService servicioItem;

	@Test
	void contextLoads() {
	}
	/*
	Requiere insertar en bd http://localhost/phpmyadmin/

			-- Carrito de prueba
		INSERT INTO carrito (
				id_carrito,
				id_usuario,
				fecha_creacion
				)
		VALUES (
	    1,
				1,
		'2025-01-01 00:00:00',
	);

	-- Item 1
		INSERT INTO carrito_item (
				id_item,
				id_carrito,
				id_producto,
				cantidad
				)
		VALUES (
	    1,
				1,
				1,
				2
		);

	-- Item 2
		INSERT INTO carrito_item (
				id_item,
				id_carrito,
				id_producto,
				cantidad
				)
		VALUES (
	    2,
				1,
				3,
				1
		);
	*/
	@Test
	@DisplayName("Verificar que existe el carrito")
	void checkCarritoExiste() {

		Carrito carrito = servicioCarrito.obtenerPorId(1);

		log.info("Verificando carrito {}", carrito.getIdCarrito());

		assertNotNull(carrito);
	}

	@Test
	@DisplayName("Verificar actualización de cantidad de un item")
	void checkActualizarCantidad() {

		CarritoItem Item = new CarritoItem();

		Item.setCantidad(5);

		CarritoItem actualizado = servicioItem.actualizarCantidad(1, Item);

		log.info("Verificando cantidad actualizada del item {}", actualizado.getIdCarritoItem());

		assertNotNull(actualizado);
		assertEquals(5, actualizado.getCantidad());
	}

	@Test
	@DisplayName("Verificar que todos los item tengan una cantidad mayor a cero")
	void checkCantidaditem() {

		List<CarritoItem> item = servicioItem.listarPorCarrito(1);

		log.info("Verificando cantidad de cada item del carrito");

		for (CarritoItem i : item) {
			assertTrue(i.getCantidad() > 0);
		}
	}

}