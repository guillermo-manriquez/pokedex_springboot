package com.pokemon.carrito_service.Service;

import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Repository.CarritoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoItemService {

    private final CarritoItemRepository carritoItemRepository;

    public CarritoItemService(CarritoItemRepository carritoItemRepository) {
        this.carritoItemRepository = carritoItemRepository;
    }

    // Agregar item
    public CarritoItem agregarItem(CarritoItem item) {
        return carritoItemRepository.save(item);
    }

    // Listar items por carrito
    public List<CarritoItem> listarPorCarrito(Integer idCarrito) {
        return carritoItemRepository.findByIdCarrito(idCarrito);
    }

    // Actualizar cantidad
    public CarritoItem actualizarCantidad(Integer id, CarritoItem nuevoItem) {

        CarritoItem item = carritoItemRepository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        item.setCantidad(nuevoItem.getCantidad());

        return carritoItemRepository.save(item);
    }

    // Eliminar item
    public boolean eliminarItem(Integer id) {

        CarritoItem item = carritoItemRepository.findById(id).orElse(null);

        if (item == null) {
            return false;
        }

        carritoItemRepository.delete(item);
        return true;
    }
}