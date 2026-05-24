package com.pokemon.carrito_service.Repository;

import com.pokemon.carrito_service.Model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {

    List<CarritoItem> findByIdCarrito(Integer idCarrito);
}