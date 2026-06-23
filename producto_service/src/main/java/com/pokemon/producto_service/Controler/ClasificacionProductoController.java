package com.pokemon.producto_service.Controler;
import com.pokemon.producto_service.Model.ClasificacionProducto;

import com.pokemon.producto_service.Service.ClasificacionProductoService;

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
@RequestMapping("/api/v1/clasificaciones")
@Tag(name="API Clasificacion Producto",description = "API para la gestion de clasificaciones de productos")
public class ClasificacionProductoController {

    private final ClasificacionProductoService clasificacionService;

    public ClasificacionProductoController(
            ClasificacionProductoService clasificacionService
    ) {

        this.clasificacionService = clasificacionService;
    }

    // Crear clasificacion
    @Operation(summary ="Crear clasificacion",description = "Endpoint permite crear una nueva clasificacion de producto")
    @ApiResponse(responseCode="201",description = "Clasificacion creada correctamente")
    @ApiResponse(responseCode="400",description = "Datos invalidos para crear clasificacion")
    @PostMapping
    public ResponseEntity<ClasificacionProducto> crearClasificacion(@Valid @RequestBody ClasificacionProducto clasificacion) {
        ClasificacionProducto nueva = clasificacionService.crearClasificacion(clasificacion);
        if (nueva != null) {
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Listar todas
    @Operation(summary ="Obtener todas las clasificaciones",description = "Endpoint permite consultar todas las clasificaciones de productos")
    @ApiResponse(responseCode="200",description = "Consulta exitosa, se entrega la lista de clasificaciones")
    @ApiResponse(responseCode="204",description = "Consulta exitosa, pero no se encontraron datos")
    @GetMapping
    public ResponseEntity<List<ClasificacionProducto>> listarClasificaciones() {

        List<ClasificacionProducto> clasificaciones = clasificacionService.listarClasificaciones();

        if (clasificaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clasificaciones, HttpStatus.OK);
    }

    // Buscar por id
    @Operation(summary ="Obtener clasificacion por id",description = "Endpoint permite consultar una clasificacion mediante su identificador")
    @ApiResponse(responseCode="200",description = "Clasificacion encontrada correctamente")
    @ApiResponse(responseCode="404",description = "Clasificacion no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ClasificacionProducto> obtenerPorId(
            @Parameter(description = "ID de la clasificacion a buscar")
            @PathVariable Integer id) {

        ClasificacionProducto clasificacion =
                clasificacionService.obtenerPorId(id);

        if (clasificacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clasificacion, HttpStatus.OK);
    }

    // Actualizar
    @Operation(summary ="Actualizar clasificacion",description = "Endpoint permite modificar una clasificacion existente")
    @ApiResponse(responseCode="200",description = "Clasificacion actualizada correctamente")
    @ApiResponse(responseCode="404",description = "Clasificacion no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ClasificacionProducto> actualizarClasificacion(
            @Parameter(description = "ID de la clasificacion a actualizar")
            @PathVariable Integer id, @Valid @RequestBody ClasificacionProducto clasificacion) {

        ClasificacionProducto actualizada = clasificacionService.actualizarClasificacion(id, clasificacion);

        if (actualizada == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // Eliminar
    @Operation(summary ="Eliminar clasificacion",description = "Endpoint permite eliminar una clasificacion mediante su identificador")
    @ApiResponse(responseCode="200",description = "Clasificacion eliminada correctamente")
    @ApiResponse(responseCode="404",description = "Clasificacion no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClasificacion(
            @Parameter(description = "ID de la clasificacion a eliminar")
            @PathVariable Integer id) {

        boolean eliminado = clasificacionService.eliminarClasificacion(id);

        if (!eliminado) {

            return new ResponseEntity<>("Clasificacion no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Clasificacion eliminada", HttpStatus.OK);
    }
}