package com.pokedex.pago_service.Repository;

import com.pokedex.pago_service.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByOrderId(Integer orderId);
}
