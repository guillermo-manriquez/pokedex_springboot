package com.pokemon.orden_service.Service;

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

    private static final Logger log =
            LoggerFactory.getLogger(OrdenService.class);

    // Crear orden
    public Orden crearOrden(Orden orden) {

        try {
            log.info("Creando orden para usuario: {}", orden.getIdUsuario());

            UserDTO usuario = userClient.obtenerUsuario(orden.getIdUsuario().longValue());
            log.info("Usuario encontrado: {}", usuario.getUsername());

            CarritoDTO carrito = carritoClient.obtenerCarrito(orden.getIdCarrito().longValue());
            log.info("Carrito validado: {}", carrito.getIdCarrito());

            orden.setFechaOrden(LocalDateTime.now());
            orden.setEstadoOrden("PENDIENTE");

            if (orden.getItems() != null) {
                for (OrdenItem item : orden.getItems()) {
                    item.setOrden(orden);
                }
            }
            Orden savedOrden = ordenRepository.save(orden);
            log.info("Orden creada con id: {}", savedOrden.getIdOrden());
            return savedOrden;

        } catch (Exception e) {
            log.error("Error al crear orden", e);
            return null;
        }
    }

    // Obtener por usuario
    public List<Orden> listarPorUsuario(Integer idUsuario) {

        try {
            userClient.obtenerUsuario(idUsuario.longValue());
            return ordenRepository.findByIdUsuario(idUsuario);

        } catch (Exception e) {
            log.error("Error al listar órdenes por usuario", e);
            return List.of();
        }
    }

    // Obtener por id
    public Orden obtenerPorId(Integer id) {

        try {
            return ordenRepository.findById(id).orElse(null);

        } catch (Exception e) {
            log.error("Error al obtener orden", e);
            return null;
        }
    }

    // Actualizar estado
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

    // Eliminar orden
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