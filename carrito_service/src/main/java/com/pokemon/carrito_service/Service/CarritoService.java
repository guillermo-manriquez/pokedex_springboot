package com.pokemon.carrito_service.Service;

import com.pokemon.carrito_service.client.UserClient;
import com.pokemon.carrito_service.dto.UserCarritoDTO;
import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Repository.CarritoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CarritoService {

    private final UserClient userClient;
    private final CarritoRepository carritoRepository;

    public CarritoService(
            UserClient userClient,
            CarritoRepository carritoRepository
    ) {
        this.userClient = userClient;
        this.carritoRepository = carritoRepository;
    }

    // Crear carrito
    public Carrito crearCarrito(Carrito carrito) {
        try {
            UserCarritoDTO user = userClient.obtenerUsuario(carrito.getIdUsuario().longValue());
            if (user == null) {
                return null;
            }
            carrito.setFechaCreacion(LocalDateTime.now());
            return carritoRepository.save(carrito);
        }catch (Exception e){
            log.error("Error al crear el carrito: ", e);
            return null;
        }
    }

    // Buscar por usuario
    public List<Carrito> listarPorUsuario(Long idUsuario) {
        try {
            UserCarritoDTO user = userClient.obtenerUsuario(idUsuario);
            if (user == null) {
                return null;
            }
            return carritoRepository.findByIdUsuario(idUsuario);
        } catch(Exception e){
                log.error("Error al buscar carritos: ", e);
                return List.of();
            }
    }

    // Obtener por ID
    public Carrito obtenerPorId(Integer id) {
        try {
            return carritoRepository.findById(id).orElse(null);
        }catch(Exception e){
            log.error("Error al buscar carrito: ", e);
            return null;
        }
    }

    // Eliminar carrito
    public boolean eliminarCarrito(Integer id) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null) {
                return false;
            }
            carritoRepository.delete(carrito);
        }catch(Exception e){
            log.error("Error al eliminar el carrito: ", e);
            return false;
        }
        return true;
    }
}