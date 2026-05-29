package com.pokemon.producto_service.Service;

import com.pokemon.producto_service.Model.ClasificacionProducto;

import com.pokemon.producto_service.Repository.ClasificacionProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasificacionProductoService {

    private final ClasificacionProductoRepository clasificacionRepository;

    public ClasificacionProductoService(
            ClasificacionProductoRepository clasificacionRepository
    ) {

        this.clasificacionRepository = clasificacionRepository;
    }

    // Crear clasificacion
    public ClasificacionProducto crearClasificacion(
            ClasificacionProducto clasificacion
    ) {

        return clasificacionRepository.save(clasificacion);
    }

    // Listar todas
    public List<ClasificacionProducto> listarClasificaciones() {

        return clasificacionRepository.findAll();
    }

    // Buscar por id
    public ClasificacionProducto obtenerPorId(Integer id) {

        return clasificacionRepository.findById(id).orElse(null);
    }

    // Actualizar
    public ClasificacionProducto actualizarClasificacion(
            Integer id,
            ClasificacionProducto nuevaClasificacion
    ) {

        ClasificacionProducto clasificacion = clasificacionRepository.findById(id).orElse(null);

        if (clasificacion == null) {
            return null;
        }

        clasificacion.setNombreCategoria(nuevaClasificacion.getNombreCategoria());

        return clasificacionRepository.save(clasificacion);
    }

    // Eliminar
    public boolean eliminarClasificacion(Integer id) {

        ClasificacionProducto clasificacion = clasificacionRepository.findById(id).orElse(null);

        if (clasificacion == null) {
            return false;
        }

        clasificacionRepository.delete(clasificacion);

        return true;
    }
}