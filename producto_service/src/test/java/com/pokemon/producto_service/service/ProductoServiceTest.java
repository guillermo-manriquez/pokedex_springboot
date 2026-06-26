package com.pokemon.producto_service.service;

import com.pokemon.producto_service.Model.Producto;
import com.pokemon.producto_service.Repository.ProductoRepository;
import com.pokemon.producto_service.Service.ProductoService;
import com.pokemon.producto_service.client.PkmClient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class ProductoServiceTest {

    @Mock
    ProductoRepository productoRepository;

    @Mock
    PkmClient pkmClient;

    @InjectMocks
    ProductoService servicio;

    Producto producto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        producto = new Producto();

        producto.setIdProducto(1);
        producto.setIdPokemon(3);
        producto.setNombreProducto("Figura Venusaur");
        producto.setDescripcion("Figura oficial");
        producto.setPrecio(new BigDecimal("9990"));
        producto.setStock(10);
    }

    @Test
    @DisplayName("Verificar que el producto existe")
    void checkProducto() {

        when(productoRepository.findById(1))
                .thenReturn(Optional.of(producto));

        Producto resultado = servicio.obtenerPorId(1);

        log.info("Producto encontrado {}", resultado.getNombreProducto());

        assertNotNull(resultado);

        verify(productoRepository).findById(1);
    }

    @Test
    @DisplayName("Verificar nombre producto")
    void checkNombreProducto() {

        when(productoRepository.findById(1))
                .thenReturn(Optional.of(producto));

        Producto resultado = servicio.obtenerPorId(1);

        log.info("Nombre producto {}", resultado.getNombreProducto());

        assertEquals("Figura Venusaur", resultado.getNombreProducto());

        verify(productoRepository).findById(1);
    }

    @Test
    @DisplayName("Verificar cantidad de productos")
    void checkCantidadProductos() {

        Producto producto2 = new Producto();

        producto2.setIdProducto(2);
        producto2.setNombreProducto("Figura Bulbasaur");

        List<Producto> productos = Arrays.asList(
                producto,
                producto2
        );

        when(productoRepository.findAll())
                .thenReturn(productos);

        int cantidad = servicio.listarProductos().size();

        log.info("Cantidad productos {}", cantidad);

        assertEquals(2, cantidad);

        verify(productoRepository).findAll();
    }

}