package com.pokemon.carrito_service.Service;

import com.pokemon.carrito_service.dto.ProductoCarritoDTO;
import com.pokemon.carrito_service.client.ProductoClient;
import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Repository.CarritoItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarritoItemService {

    private final ProductoClient productoClient;
    private final CarritoItemRepository carritoItemRepository;

    public CarritoItemService(
            ProductoClient productoClient,
            CarritoItemRepository carritoItemRepository
    )
    {
        this.productoClient = productoClient;
        this.carritoItemRepository = carritoItemRepository;
    }

    // Agregar item
    public CarritoItem agregarItem(CarritoItem item) {

        try {
            ProductoCarritoDTO producto = productoClient.obtenerProducto(item.getIdProducto());
            if (producto == null) {
                return null;
            }
            log.info("Producto encontrado: " + producto.getNombreProducto());
            return carritoItemRepository.save(item);
        } catch (Exception e) {
            log.error("Error al agregar item al carrito", e);
            return null;
        }
    }

    // Listar items por carrito
    public List<CarritoItem> listarPorCarrito(Integer idCarrito) {
        try {
            return carritoItemRepository.findByIdCarrito(idCarrito);
        } catch (Exception e) {
            log.error("Error al listar items del carrito", e);
            return List.of();
        }
    }

    // Actualizar cantidad
    public CarritoItem actualizarCantidad(Integer id, CarritoItem nuevoItem) {
        try {
            CarritoItem item = carritoItemRepository.findById(id).orElse(null);
            if (item == null) {
                return null;
            }
            ProductoCarritoDTO producto = productoClient.obtenerProducto(item.getIdProducto());
            if (producto == null) {
                return null;
            }
            item.setCantidad(nuevoItem.getCantidad());
            log.info("Cantidad actualizada para producto: " + producto.getNombreProducto());
            return carritoItemRepository.save(item);

        } catch (Exception e) {log.error("Error al actualizar cantidad", e);
            return null;
        }
    }

    // Eliminar item
    public boolean eliminarItem(Integer id) {

        try {
            CarritoItem item = carritoItemRepository.findById(id).orElse(null);
            if (item == null) {
                return false;
            }
            carritoItemRepository.delete(item);
            log.info("Item eliminado correctamente");
            return true;

        } catch (Exception e) {
            log.error("Error al eliminar item", e);
            return false;
        }
    }
}