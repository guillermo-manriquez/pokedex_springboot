package com.pokemon.orden_service.Service;

import com.pokemon.orden_service.Client.PagoRequest;
import com.pokemon.orden_service.Client.PagoResponse;
import com.pokemon.orden_service.Client.PagoServiceClient;
import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Model.OrdenItem;
import com.pokemon.orden_service.Repository.OrdenRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class OrdenService {

    private final PagoServiceClient pagoServiceClient;
    private final OrdenRepository ordenRepository;
    private static final Logger log = LoggerFactory.getLogger(OrdenService.class);

    // Crear orden
    public Orden crearOrden(Orden orden) {

        log.info("Creando orden para usuario: {}", orden.getIdUsuario());

        orden.setFechaOrden(LocalDateTime.now());
        orden.setEstadoOrden("Pendiente");

        if (orden.getItems() != null) {
            for (OrdenItem item : orden.getItems()) {
                item.setOrden(orden);
            }
        }

        Orden savedOrden = ordenRepository.save(orden);

        log.info("Orden creada con id: {}", savedOrden.getIdOrden());

        try {
            PagoRequest pagoRequest = new PagoRequest(
                    savedOrden.getIdOrden(),
                    savedOrden.getIdUsuario(),
                    savedOrden.getTotalMonto(),
                    "CLP",
                    "CREDIT_CARD",
                    null
            );

            log.info("Enviando request a pago-service: {}", pagoRequest);
            PagoResponse pagoResponse = pagoServiceClient.procesarPago(pagoRequest);
            log.info("Respuesta de pago-service: {}", pagoResponse);

            if ("COMPLETED".equalsIgnoreCase(pagoResponse.getStatus())) {
                savedOrden.setEstadoOrden("Pagado");
                log.info("Pago completado para orden {}",savedOrden.getIdOrden());
            } else {
                savedOrden.setEstadoOrden("Pago Fallido");
                log.warn("Pago rechazado para orden {}", savedOrden.getIdOrden());
            }
        } catch (Exception e) {
            log.error("ERROR COMPLETO FEIGN:", e);
            savedOrden.setEstadoOrden("Error Pago");
        }
        return ordenRepository.save(savedOrden);
    }

    // Obtener por usuario
    public List<Orden> listarPorUsuario(Integer idUsuario) {
        return ordenRepository.findByIdUsuario(idUsuario);
    }

    // Obtener por id
    public Orden obtenerPorId(Integer id) {
        return ordenRepository.findById(id).orElse(null);
    }

    // Actualizar estado
    public Orden actualizarEstado(Integer id, String estado) {

        Orden orden = ordenRepository.findById(id).orElse(null);

        if (orden == null) {
            return null;
        }

        orden.setEstadoOrden(estado);

        return ordenRepository.save(orden);
    }

    // Eliminar orden
    public boolean eliminarOrden(Integer id) {

        Orden orden = ordenRepository.findById(id).orElse(null);

        if (orden == null) {
            return false;
        }

        ordenRepository.delete(orden);
        return true;
    }
}