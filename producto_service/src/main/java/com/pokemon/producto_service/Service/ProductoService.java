package com.pokemon.producto_service.Service;

import com.pokemon.producto_service.Model.Producto;

import com.pokemon.producto_service.Repository.ProductoRepository;

import com.pokemon.producto_service.client.PkmClient;
import com.pokemon.producto_service.dto.PkmProductoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductoService {

    private final PkmClient pkmClient;
    private final ProductoRepository productoRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            PkmClient pkmClient
    ) {

        this.pkmClient = pkmClient;
        this.productoRepository = productoRepository;
    }

    // Crear producto
    public Producto crearProducto(Producto producto) {
        try {
            PkmProductoDTO pokemon = pkmClient.obtenerPokemon(producto.getIdPokemon());
            if (pokemon == null) {
                return null;
            }
            log.info("Pokemon encontrado: " + pokemon.getNombre()
            );
            return productoRepository.save(producto);
        } catch (Exception e) {
            log.error("Error al crear producto", e);
            return null;
        }
    }

    // Listar todos
    public List<Producto> listarProductos() {

        return productoRepository.findAll();
    }

    // Buscar por id
    public Producto obtenerPorId(Integer id) {

        return productoRepository.findById(id).orElse(null);
    }

    // Buscar por pokemon
    public List<Producto> listarPorPokemon(Integer idPokemon) {
        try {
            PkmProductoDTO pokemon = pkmClient.obtenerPokemon(idPokemon);
            if (pokemon == null) {
                return List.of();
            }
            log.info("Pokemon encontrado: " + pokemon.getNombre());
            return productoRepository.findByIdPokemon(idPokemon);
        } catch (Exception e) {
            log.error("Error al listar productos por pokemon", e);
            return List.of();
        }
    }

    // Buscar por clasificacion
    public List<Producto> listarPorClasificacion(Integer idClasificacion) {

        return productoRepository.findByClasificacionProducto_IdClasificacion(idClasificacion);
    }

    // Actualizar producto
    public Producto actualizarProducto(Integer id, Producto nuevoProducto) {

        try {

            Producto producto = productoRepository.findById(id).orElse(null);
            if (producto == null) {
                return null;
            }
            PkmProductoDTO pokemon = pkmClient.obtenerPokemon(nuevoProducto.getIdPokemon());
            if (pokemon == null) {
                return null;
            }

            log.info("Pokemon encontrado: " + pokemon.getNombre());

            producto.setNombreProducto(
                    nuevoProducto.getNombreProducto()
            );

            producto.setDescripcion(
                    nuevoProducto.getDescripcion()
            );

            producto.setPrecio(
                    nuevoProducto.getPrecio()
            );

            producto.setStock(
                    nuevoProducto.getStock()
            );

            producto.setIdPokemon(
                    nuevoProducto.getIdPokemon()
            );

            producto.setClasificacionProducto(
                    nuevoProducto.getClasificacionProducto()
            );

            return productoRepository.save(producto);

        } catch (Exception e) {

            log.error("Error al actualizar producto", e);

            return null;
        }
    }

    public boolean eliminarProducto(Integer id) {
        try {
            Producto producto = productoRepository.findById(id).orElse(null);
            if (producto == null) {
                return false;
            }
            productoRepository.delete(producto);
            log.info("Producto eliminado correctamente");
            return true;
        } catch (Exception e) {
            log.error("Error al eliminar producto", e);
            return false;
        }
    }
}