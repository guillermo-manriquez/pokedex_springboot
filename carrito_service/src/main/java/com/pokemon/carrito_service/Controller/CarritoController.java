package com.pokemon.carrito_service.Controller;

import com.pokemon.carrito_service.Model.Carrito;
import com.pokemon.carrito_service.Service.CarritoService;

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
@RequestMapping("/api/v1/carritos")
@Tag(name="API Carrito",description = "API para la gestion de carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Crear carrito
    @Operation(summary ="Crear carrito",description = "Endpoint permite crear un nuevo carrito")
    @ApiResponse(responseCode="201",description = "Carrito creado correctamente")
    @ApiResponse(responseCode="400",description = "Datos del carrito invalidos")
    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(
            @Valid @RequestBody Carrito carrito
    ) {
        Carrito nuevo = carritoService.crearCarrito(carrito);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // Carritos por usuario
    @Operation(summary ="Obtener carritos por usuario",description = "Endpoint permite consultar todos los carritos asociados a un usuario")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de carritos")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Carrito>> listarPorUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id) {

        List<Carrito> carritos = carritoService.listarPorUsuario(id);

        if (carritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

    // Obtener carrito por ID
    @Operation(summary ="Obtener carrito por id",description = "Endpoint permite consultar un carrito mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del carrito")
    @ApiResponse(responseCode="404",description = "Carrito no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@Parameter(description = "ID del carrito") @PathVariable Integer id) {

        Carrito carrito = carritoService.obtenerPorId(id);

        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    // Eliminar carrito
    @Operation(summary ="Eliminar carrito",description = "Endpoint permite eliminar un carrito mediante su identificador")
    @ApiResponse(responseCode="200",description = "Carrito eliminado correctamente")
    @ApiResponse(responseCode="404",description = "Carrito no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCarrito(@Parameter(description = "ID del carrito") @PathVariable Integer id) {

        boolean eliminado = carritoService.eliminarCarrito(id);

        if (!eliminado) {
            return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Carrito eliminado", HttpStatus.OK);
    }
}