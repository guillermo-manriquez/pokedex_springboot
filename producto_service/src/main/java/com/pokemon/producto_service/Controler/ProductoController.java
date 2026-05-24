package com.pokemon.producto_service.Controler;

import com.pokemon.producto_service.Model.Producto;

import com.pokemon.producto_service.Service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(
            ProductoService productoService
    ) {

        this.productoService = productoService;
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<Producto>
    crearProducto(@Valid @RequestBody Producto producto) {

        Producto nuevoProducto = productoService.crearProducto(producto);

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Producto>>
    listarProductos() {

        List<Producto> productos = productoService.listarProductos();

        if (productos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Producto>
    obtenerPorId(@PathVariable Integer id) {

        Producto producto =
                productoService.obtenerPorId(id);

        if (producto == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Buscar por pokemon
    @GetMapping("/pokemon/{idPokemon}")
    public ResponseEntity<List<Producto>> listarPorPokemon(@PathVariable Integer idPokemon) {

        List<Producto> productos =
                productoService.listarPorPokemon(idPokemon);

        if (productos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar por clasificacion
    @GetMapping("/clasificacion/{idClasificacion}")
    public ResponseEntity<List<Producto>> listarPorClasificacion(@PathVariable Integer idClasificacion) {

        List<Producto> productos = productoService.listarPorClasificacion(idClasificacion);

        if (productos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Producto>
    actualizarProducto(
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto
    ) {

        Producto actualizado = productoService.actualizarProducto(id, producto);

        if (actualizado == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer id) {

        boolean eliminado = productoService.eliminarProducto(id);

        if (!eliminado) {

            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
    }
}