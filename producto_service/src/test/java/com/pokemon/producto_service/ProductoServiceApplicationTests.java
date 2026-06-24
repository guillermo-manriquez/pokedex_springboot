package com.pokemon.producto_service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.pokemon.producto_service.Model.Producto;
import com.pokemon.producto_service.Service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class ProductoServiceApplicationTests {

	@Autowired
	ProductoService servicio;


	/*
	Requiere insertar en bd http://localhost/phpmyadmin/

	INSERT INTO clasificacion_producto(nombre_categoria)
	VALUES ('Figuras');

	INSERT INTO producto(
			id_clasificacion,
			id_pokemon,
			nombre_producto,
			descripcion,
			precio,
			stock
			)
	VALUES(
	        1,
			3,
			'Figura Venusaur',
			'Figura oficial',
			9990,
			10

	);

	INSERT INTO producto(
			id_clasificacion,
			id_pokemon,
			nombre_producto,
			descripcion,
			precio,
			stock
			)
	VALUES(
	        1,
			1,
			'Figura bulbasaur',
			'Figura oficial',
			9990,
			10

	);*/

	@Test
	@DisplayName("Verificar que el producto existe")
	void checkProducto(){

		Producto producto = servicio.obtenerPorId(1);

		log.info("Producto encontrado {}",producto.getNombreProducto());

		assertNotNull(producto);
	}

	@Test
	@DisplayName("Verificar nombre producto")
	void checkNombreProducto(){

		Producto producto = servicio.obtenerPorId(1);

		log.info("Nombre producto {}",producto.getNombreProducto());

		assertEquals("Figura Venusaur",producto.getNombreProducto());
	}

	@Test
	@DisplayName("Verificar cantidad de productos")
	void checkCantidadProductos(){

		int cantidad = servicio.listarProductos().size();

		log.info("Cantidad productos {}",cantidad);

		assertEquals(2,cantidad);
	}
}
