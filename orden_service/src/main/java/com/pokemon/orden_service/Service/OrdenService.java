package com.pokemon.orden_service.Service;

import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Model.OrdenItem;
import com.pokemon.orden_service.Repository.OrdenRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    // Crear orden
    public Orden crearOrden(Orden orden) {

        orden.setFechaOrden(LocalDateTime.now());
        orden.setEstadoOrden("Pendiente");

        if (orden.getItems() != null) {
            for (OrdenItem item : orden.getItems()) {
                item.setOrden(orden);
            }
        }

        return ordenRepository.save(orden);
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