package com.pokemon.carrito_service.Controller;

import com.pokemon.carrito_service.Model.CarritoItem;
import com.pokemon.carrito_service.Service.CarritoItemService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carritos/carrito-items")
@Tag(name="API Carrito Item",description = "API para la gestion de items del carrito")
public class CarritoItemController {

    private final CarritoItemService carritoItemService;

    public CarritoItemController(CarritoItemService carritoItemService) {
        this.carritoItemService = carritoItemService;
    }

    // Agregar item
    @Operation(summary ="Agregar item al carrito",description = "Endpoint permite agregar un item a un carrito")
    @ApiResponse(responseCode="201",description = "Item agregado correctamente")
    @ApiResponse(responseCode="400",description = "Datos del item invalidos")
    @PostMapping
    public ResponseEntity<CarritoItem> agregarItem(
            @Valid @RequestBody CarritoItem item
    ) {
        CarritoItem nuevo = carritoItemService.agregarItem(item);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Items por carrito
    @Operation(summary ="Obtener items por carrito",description = "Endpoint permite consultar todos los items asociados a un carrito")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de items")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/carrito/{id}")
    public ResponseEntity<List<CarritoItem>> listarPorCarrito(@Parameter(description = "ID del carrito") @PathVariable Integer id) {

        List<CarritoItem> items = carritoItemService.listarPorCarrito(id);

        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Actualizar cantidad
    @Operation(summary ="Actualizar cantidad de item",description = "Endpoint permite modificar la cantidad de un item del carrito")
    @ApiResponse(responseCode="200",description = "Cantidad actualizada correctamente")
    @ApiResponse(responseCode="404",description = "Item no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<CarritoItem> actualizarCantidad(
            @Parameter(description = "ID del item")
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
    @Operation(summary ="Eliminar item del carrito",description = "Endpoint permite eliminar un item mediante su identificador")
    @ApiResponse(responseCode="200",description = "Item eliminado correctamente")
    @ApiResponse(responseCode="404",description = "Item no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarItem(@Parameter(description = "ID del item") @PathVariable Integer id) {

        boolean eliminado = carritoItemService.eliminarItem(id);

        if (!eliminado) {
            return new ResponseEntity<>("Item no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Item eliminado", HttpStatus.OK);
    }
}