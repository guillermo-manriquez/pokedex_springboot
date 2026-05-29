package com.pokemon.producto_service.Controler;
import com.pokemon.producto_service.Model.ClasificacionProducto;

import com.pokemon.producto_service.Service.ClasificacionProductoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clasificaciones")
public class ClasificacionProductoController {

    private final ClasificacionProductoService clasificacionService;

    public ClasificacionProductoController(
            ClasificacionProductoService clasificacionService
    ) {

        this.clasificacionService = clasificacionService;
    }

    // Crear clasificacion
    @PostMapping
    public ResponseEntity<ClasificacionProducto> crearClasificacion(@Valid @RequestBody ClasificacionProducto clasificacion) {
        ClasificacionProducto nueva = clasificacionService.crearClasificacion(clasificacion);
        if (nueva != null) {
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // Listar todas
    @GetMapping
    public ResponseEntity<List<ClasificacionProducto>> listarClasificaciones() {

        List<ClasificacionProducto> clasificaciones = clasificacionService.listarClasificaciones();

        if (clasificaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clasificaciones, HttpStatus.OK);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ClasificacionProducto> obtenerPorId(@PathVariable Integer id) {

        ClasificacionProducto clasificacion =
                clasificacionService.obtenerPorId(id);

        if (clasificacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clasificacion, HttpStatus.OK);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ClasificacionProducto> actualizarClasificacion(@PathVariable Integer id, @Valid @RequestBody ClasificacionProducto clasificacion) {

        ClasificacionProducto actualizada = clasificacionService.actualizarClasificacion(id, clasificacion);

        if (actualizada == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClasificacion(@PathVariable Integer id) {

        boolean eliminado = clasificacionService.eliminarClasificacion(id);

        if (!eliminado) {

            return new ResponseEntity<>("Clasificacion no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Clasificacion eliminada", HttpStatus.OK);
    }
}