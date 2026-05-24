package com.pokemon.orden_service.Repository;

import com.pokemon.orden_service.Model.OrdenItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenItemRepository extends JpaRepository<OrdenItem, Integer> {
}