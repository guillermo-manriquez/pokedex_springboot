package com.pokemon.producto_service.Repository;

import com.pokemon.producto_service.Model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {

    List<Producto> findByIdPokemon(Integer idPokemon);

    List<Producto> findByClasificacionProducto_IdClasificacion(
            Integer idClasificacion
    );
}