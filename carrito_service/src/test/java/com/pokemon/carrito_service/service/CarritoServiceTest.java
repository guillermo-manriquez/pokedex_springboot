package com.pokemon.carrito_service.service;

import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Repository.CarritoRepository;
import com.pokemon.carrito_service.Repository.CarritoItemRepository;
import com.pokemon.carrito_service.Service.CarritoService;
import com.pokemon.carrito_service.Service.CarritoItemService;
import com.pokemon.carrito_service.client.UserClient;
import com.pokemon.carrito_service.client.ProductoClient;
import com.pokemon.carrito_service.dto.ProductoCarritoDTO;

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
class CarritoServiceTest {

    @Mock
    CarritoRepository carritoRepository;

    @Mock
    CarritoItemRepository carritoItemRepository;

    @Mock
    UserClient userClient;

    @Mock
    ProductoClient productoClient;

    @InjectMocks
    CarritoService servicioCarrito;

    @InjectMocks
    CarritoItemService servicioItem;

    Carrito carrito;
    CarritoItem item;
    ProductoCarritoDTO producto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        carrito = new Carrito();
        carrito.setIdCarrito(1);
        carrito.setIdUsuario(1L);

        item = new CarritoItem();
        item.setIdCarritoItem(1);
        item.setIdCarrito(1);
        item.setIdProducto(1);
        item.setCantidad(2);

        producto = new ProductoCarritoDTO();
        producto.setNombreProducto("Poción");

    }

    @Test
    @DisplayName("Verificar que existe el carrito")
    void checkCarritoExiste() {
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));

        Carrito resultado = servicioCarrito.obtenerPorId(1);

        log.info("Verificando carrito {}", resultado.getIdCarrito());

        assertNotNull(resultado);

        verify(carritoRepository).findById(1);
    }

    @Test
    @DisplayName("Verificar actualización de cantidad de un item")
    void checkActualizarCantidad() {

        CarritoItem nuevoItem =
                new CarritoItem();

        nuevoItem.setCantidad(5);

        when(carritoItemRepository.findById(1)).thenReturn(Optional.of(item));

        when(productoClient.obtenerProducto(1)).thenReturn(producto);

        when(carritoItemRepository.save(item)).thenReturn(item);

        CarritoItem actualizado = servicioItem.actualizarCantidad(1, nuevoItem);

        log.info("Verificando cantidad actualizada del item {}", actualizado.getIdCarritoItem());

        assertNotNull(actualizado);

        assertEquals(5, actualizado.getCantidad());

        verify(carritoItemRepository).findById(1);

        verify(productoClient).obtenerProducto(1);

        verify(carritoItemRepository).save(item);

    }

    @Test
    @DisplayName("Verificar que todos los item tengan una cantidad mayor a cero")
    void checkCantidaditem() {

        CarritoItem item2 =
                new CarritoItem();

        item2.setCantidad(3);

        List<CarritoItem> lista =
                Arrays.asList(
                        item,
                        item2
                );
        when(carritoItemRepository.findByIdCarrito(1)).thenReturn(lista);

        List<CarritoItem> resultado = servicioItem.listarPorCarrito(1);

        log.info("Verificando cantidad de cada item del carrito");

        for(CarritoItem i : resultado){
            assertTrue(i.getCantidad() > 0);

        }

        verify(carritoItemRepository).findByIdCarrito(1);

    }

}

