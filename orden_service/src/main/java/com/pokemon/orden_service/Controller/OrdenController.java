package com.pokemon.orden_service.Controller;

import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Service.OrdenService;

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
@RequestMapping("/api/v1/ordenes")
@Tag(name="API Orden",description = "API para la gestion de ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Crear orden
    @Operation(summary ="Crear orden",description = "Endpoint permite crear una nueva orden")
    @ApiResponse(responseCode="201",description = "Orden creada correctamente")
    @ApiResponse(responseCode="400",description = "Datos de la orden invalidos")
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@Valid @RequestBody Orden orden) {

        Orden nueva = ordenService.crearOrden(orden);

        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Listar por usuario
    @Operation(summary ="Obtener ordenes por usuario",description = "Endpoint permite consultar todas las ordenes de un usuario")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de ordenes")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Orden>> listarPorUsuario(@Parameter(description = "ID del usuario") @PathVariable Integer id) {

        List<Orden> ordenes = ordenService.listarPorUsuario(id);

        if (ordenes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    // Obtener por id
    @Operation(summary ="Obtener orden por id",description = "Endpoint permite consultar una orden mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion de la orden")
    @ApiResponse(responseCode="404",description = "Orden no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@Parameter(description = "ID de la orden") @PathVariable Integer id) {

        Orden orden = ordenService.obtenerPorId(id);

        if (orden == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orden, HttpStatus.OK);
    }

    // Actualizar estado
    @Operation(summary ="Actualizar estado de orden",description = "Endpoint permite actualizar el estado de una orden")
    @ApiResponse(responseCode="200",description = "Estado actualizado correctamente")
    @ApiResponse(responseCode="404",description = "Orden no encontrada")
    @PutMapping("/{id}/estado")
    public ResponseEntity<Orden> actualizarEstado(
            @Parameter(description = "ID de la orden")
            @PathVariable Integer id,
            @RequestParam String estado
    ) {

        Orden actualizada = ordenService.actualizarEstado(id, estado);

        if (actualizada == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // Eliminar orden
    @Operation(summary ="Eliminar orden",description = "Endpoint permite eliminar una orden mediante su identificador")
    @ApiResponse(responseCode="200",description = "Orden eliminada correctamente")
    @ApiResponse(responseCode="404",description = "Orden no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOrden(@Parameter(description = "ID de la orden") @PathVariable Integer id) {

        boolean eliminado = ordenService.eliminarOrden(id);

        if (!eliminado) {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Orden eliminada", HttpStatus.OK);
    }
}