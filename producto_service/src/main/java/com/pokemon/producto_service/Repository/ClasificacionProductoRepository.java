package com.pokemon.producto_service.Repository;

import com.pokemon.producto_service.Model.ClasificacionProducto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasificacionProductoRepository
        extends JpaRepository<ClasificacionProducto, Integer> {
}
