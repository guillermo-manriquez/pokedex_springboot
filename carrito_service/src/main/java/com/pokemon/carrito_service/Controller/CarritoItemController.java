package com.pokemon.carrito_service.Controller;

import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Service.CarritoItemService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrito-items")
public class CarritoItemController {

    private final CarritoItemService carritoItemService;

    public CarritoItemController(CarritoItemService carritoItemService) {
        this.carritoItemService = carritoItemService;
    }

    // Agregar item
    @PostMapping
    public ResponseEntity<CarritoItem> agregarItem(
            @Valid @RequestBody CarritoItem item
    ) {
        CarritoItem nuevo = carritoItemService.agregarItem(item);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Items por carrito
    @GetMapping("/carrito/{id}")
    public ResponseEntity<List<CarritoItem>> listarPorCarrito(@PathVariable Integer id) {

        List<CarritoItem> items = carritoItemService.listarPorCarrito(id);

        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Actualizar cantidad
    @PutMapping("/{id}")
    public ResponseEntity<CarritoItem> actualizarCantidad(
            @PathVariable Integer id,
            @RequestBody CarritoItem item
    ) {

        CarritoItem actualizado = carritoItemService.actualizarCantidad(id, item);

        if (actualizado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // Eliminar item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarItem(@PathVariable Integer id) {

        boolean eliminado = carritoItemService.eliminarItem(id);

        if (!eliminado) {
            return new ResponseEntity<>("Item no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Item eliminado", HttpStatus.OK);
    }
}