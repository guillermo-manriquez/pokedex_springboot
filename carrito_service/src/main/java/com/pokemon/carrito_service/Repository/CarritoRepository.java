package com.pokemon.carrito_service.Repository;

import com.pokemon.carrito_service.Model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    List<Carrito> findByIdUsuario(Integer idUsuario);
}