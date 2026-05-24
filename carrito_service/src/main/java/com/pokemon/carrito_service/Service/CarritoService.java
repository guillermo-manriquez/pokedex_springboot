package com.pokemon.carrito_service.Service;

import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    // Crear carrito
    public Carrito crearCarrito(Carrito carrito) {
        carrito.setFechaCreacion(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    // Buscar por usuario
    public List<Carrito> listarPorUsuario(Integer idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    // Obtener por ID
    public Carrito obtenerPorId(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    // Eliminar carrito
    public boolean eliminarCarrito(Integer id) {

        Carrito carrito = carritoRepository.findById(id).orElse(null);

        if (carrito == null) {
            return false;
        }

        carritoRepository.delete(carrito);
        return true;
    }
}