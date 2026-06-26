package com.pokemon.orden_service;

import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Service.OrdenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrdenServiceApplicationTests {

	@Autowired
	OrdenService servicio;

	/*
	Requiere insertar en bd http://localhost/phpmyadmin/

	INSERT INTO orden
	(
	    id_orden,
	    id_carrito,
	    id_usuario,
	    fecha_orden,
	    estado_orden,
	    total_monto
	)
	VALUES
	(
	    1,
	    1,
	    1,
	    '2025-01-01 00:00:00',
	    'PAGADO',
	    15000.00
	);

	INSERT INTO orden_item
	(
	    id_orden_item,
	    id_producto,
	    cantidad,
	    precio_historico,
	    id_orden
	)
	VALUES
	(
	    1,
	    1,
	    2,
	    7500.00,
	    1
	);
	*/

	@Test
	@DisplayName("Verificar que la orden existe")
	void checkOrdenExiste() {

		Orden orden = servicio.obtenerPorId(1);

		log.info("Verificando existencia de orden");

		assertNotNull(orden);
	}

	@Test
	@DisplayName("Verificar que la orden tiene usuario asociado")
	void checkOrdenUsuario() {

		Orden orden = servicio.obtenerPorId(1);

		log.info("Verificando usuario de la orden {}", orden.getIdOrden());

		assertNotNull(orden.getIdUsuario());
	}

	@Test
	@DisplayName("Verificar estado de la orden")
	void checkEstadoOrden() {

		Orden orden = servicio.obtenerPorId(1);

		log.info("Verificando estado de la orden");

		assertEquals("PAGADO", orden.getEstadoOrden());
	}

}