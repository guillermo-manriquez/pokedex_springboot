package com.pokemon.orden_service.Repository;

import com.pokemon.orden_service.Model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    List<Orden> findByIdUsuario(Integer idUsuario);
}