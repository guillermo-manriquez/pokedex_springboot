package com.pokemon.orden_service.Service;

import com.pokemon.orden_service.dto.PagoRequest;
import com.pokemon.orden_service.dto.PagoResponse;
import com.pokemon.orden_service.client.PagoServiceClient;
import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Model.OrdenItem;
import com.pokemon.orden_service.Repository.OrdenRepository;

import com.pokemon.orden_service.client.UserClient;
import com.pokemon.orden_service.client.CarritoClient;

import com.pokemon.orden_service.dto.UserDTO;
import com.pokemon.orden_service.dto.CarritoDTO;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final UserClient userClient;
    private final CarritoClient carritoClient;
    private final PagoServiceClient pagoServiceClient;

    private static final Logger log =
            LoggerFactory.getLogger(OrdenService.class);

    // crear orden
    public Orden crearOrden(Orden orden) {

        try {
            log.info("Iniciando creación de orden para usuario: {}", orden.getIdUsuario());

            UserDTO usuario = userClient.obtenerUsuario(orden.getIdUsuario().longValue());
            if (usuario == null) {
                throw new RuntimeException("Usuario no existe");
            }
            log.info("Usuario validado: {}", usuario.getUsername());


            CarritoDTO carrito = carritoClient.obtenerCarrito(orden.getIdCarrito().longValue());
            if (carrito == null) {
                throw new RuntimeException("Carrito no existe");
            }
            log.info("Carrito validado: {}", carrito.getIdCarrito());


            orden.setFechaOrden(LocalDateTime.now());
            orden.setEstadoOrden("PENDIENTE");

            if (orden.getItems() != null) {
                for (OrdenItem item : orden.getItems()) {
                    item.setOrden(orden);
                }
            }

            Orden savedOrden = ordenRepository.save(orden);
            log.info("Orden creada con ID: {}", savedOrden.getIdOrden());

            try {
                PagoRequest pagoRequest = new PagoRequest(
                        savedOrden.getIdOrden(),
                        savedOrden.getIdUsuario(),
                        savedOrden.getTotalMonto(),
                        "CLP",
                        "CREDIT_CARD",
                        null
                );

                log.info("Enviando pago-service: {}", pagoRequest);

                PagoResponse pagoResponse =
                        pagoServiceClient.procesarPago(pagoRequest);

                log.info("Respuesta pago-service: {}", pagoResponse);

                if ("COMPLETED".equalsIgnoreCase(pagoResponse.getStatus())) {
                    savedOrden.setEstadoOrden("PAGADO");
                } else {
                    savedOrden.setEstadoOrden("PAGO_FALLIDO");
                }

            } catch (Exception e) {
                log.error("Error en pago-service", e);
                savedOrden.setEstadoOrden("ERROR_PAGO");
            }

            return ordenRepository.save(savedOrden);

        } catch (Exception e) {
            log.error("Error creando orden", e);
            throw new RuntimeException("No se pudo crear la orden");
        }
    }

    // listar por usuario
    public List<Orden> listarPorUsuario(Integer idUsuario) {

        try {
            userClient.obtenerUsuario(idUsuario.longValue());
            return ordenRepository.findByIdUsuario(idUsuario);

        } catch (Exception e) {
            log.error("Error al listar órdenes por usuario", e);
            return List.of();
        }
    }

    // obtener por id
    public Orden obtenerPorId(Integer id) {

        try {
            return ordenRepository.findById(id).orElse(null);

        } catch (Exception e) {
            log.error("Error al obtener orden", e);
            return null;
        }
    }

    // actualizar estado
    public Orden actualizarEstado(Integer id, String estado) {

        try {
            Orden orden = ordenRepository.findById(id).orElse(null);

            if (orden == null) {
                return null;
            }

            orden.setEstadoOrden(estado);
            return ordenRepository.save(orden);

        } catch (Exception e) {
            log.error("Error al actualizar estado", e);
            return null;
        }
    }

    // eliminar orden
    public boolean eliminarOrden(Integer id) {

        try {
            Orden orden = ordenRepository.findById(id).orElse(null);

            if (orden == null) {
                return false;
            }

            ordenRepository.delete(orden);
            return true;

        } catch (Exception e) {
            log.error("Error al eliminar orden", e);
            return false;
        }
    }
}