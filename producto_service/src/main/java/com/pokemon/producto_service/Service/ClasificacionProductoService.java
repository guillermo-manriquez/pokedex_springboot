package com.pokemon.producto_service.Service;

import com.pokemon.producto_service.Model.ClasificacionProducto;

import com.pokemon.producto_service.Repository.ClasificacionProductoRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClasificacionProductoService {

    private final ClasificacionProductoRepository clasificacionRepository;

    public ClasificacionProductoService(
            ClasificacionProductoRepository clasificacionRepository
    ) {

        this.clasificacionRepository = clasificacionRepository;
    }

    // Crear clasificacion
    public ClasificacionProducto crearClasificacion(ClasificacionProducto clasificacion) {
        try {
            ClasificacionProducto clasenueva = clasificacionRepository.save(clasificacion);
            log.info("Clasificación creada: " + clasenueva.getNombreCategoria());
            return clasenueva;
        } catch (Exception e) {
            log.error("Error al crear clasificación", e);
            return null;
        }
    }

    // Listar todas
    public List<ClasificacionProducto> listarClasificaciones() {

        return clasificacionRepository.findAll();
    }

    // Buscar por id
    public ClasificacionProducto obtenerPorId(Integer id) {

        try {
            return clasificacionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error al obtener clasificación por id", e);
            return null;
        }
    }

    // Actualizar
    public ClasificacionProducto actualizarClasificacion(Integer id, ClasificacionProducto nuevaClasificacion) {
        try {

            ClasificacionProducto clasificacion = clasificacionRepository.findById(id).orElse(null);
            if (clasificacion == null) {
                return null;
            }
            clasificacion.setNombreCategoria(nuevaClasificacion.getNombreCategoria());
            ClasificacionProducto updated = clasificacionRepository.save(clasificacion);
            log.info("Clasificación actualizada: " + updated.getNombreCategoria());
            return updated;

        } catch (Exception e) {
            log.error("Error al actualizar clasificación", e);
            return null;
        }
    }

    // Eliminar
    public boolean eliminarClasificacion(Integer id) {

        try {
            ClasificacionProducto clasificacion = clasificacionRepository.findById(id).orElse(null);
            if (clasificacion == null) {
                return false;
            }
            clasificacionRepository.delete(clasificacion);
            log.info("Clasificación eliminada con id: " + id);
            return true;

        } catch (Exception e) {
            log.error("Error al eliminar clasificación", e);
            return false;
        }
    }
}