package com.pokemon.carrito_service.Controller;

import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Service.CarritoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Crear carrito
    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(
            @Valid @RequestBody Carrito carrito
    ) {
        Carrito nuevo = carritoService.crearCarrito(carrito);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Carritos por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Carrito>> listarPorUsuario(@PathVariable Integer id) {

        List<Carrito> carritos = carritoService.listarPorUsuario(id);

        if (carritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

    // Obtener carrito por ID
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Integer id) {

        Carrito carrito = carritoService.obtenerPorId(id);

        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    // Eliminar carrito
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCarrito(@PathVariable Integer id) {

        boolean eliminado = carritoService.eliminarCarrito(id);

        if (!eliminado) {
            return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Carrito eliminado", HttpStatus.OK);
    }
}