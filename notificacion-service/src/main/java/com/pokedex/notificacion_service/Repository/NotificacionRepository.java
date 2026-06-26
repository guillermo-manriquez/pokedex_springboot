package com.pokedex.notificacion_service.Repository;

import com.pokedex.notificacion_service.Entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUserIdOrderByCreatedAtDesc(Long userId);
}
