package com.pokemon.orden_service.Controller;

import com.pokemon.orden_service.Model.Orden;
import com.pokemon.orden_service.Service.OrdenService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Crear orden
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@Valid @RequestBody Orden orden) {

        Orden nueva = ordenService.crearOrden(orden);

        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Listar por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Orden>> listarPorUsuario(@PathVariable Integer id) {

        List<Orden> ordenes = ordenService.listarPorUsuario(id);

        if (ordenes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Integer id) {

        Orden orden = ordenService.obtenerPorId(id);

        if (orden == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orden, HttpStatus.OK);
    }

    // Actualizar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<Orden> actualizarEstado(
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOrden(@PathVariable Integer id) {

        boolean eliminado = ordenService.eliminarOrden(id);

        if (!eliminado) {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Orden eliminada", HttpStatus.OK);
    }
}