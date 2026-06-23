package com.pokemon.producto_service.Controler;

import com.pokemon.producto_service.Model.Producto;

import com.pokemon.producto_service.Service.ProductoService;

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
@RequestMapping("/api/v1/productos")
@Tag(name="API Producto",description = "API para la gestion de productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(
            ProductoService productoService
    ) {

        this.productoService = productoService;
    }

    // Crear producto
    @Operation(summary ="Crear producto",description = "Endpoint permite crear un nuevo producto")
    @ApiResponse(responseCode="201",description = "Producto creado correctamente")
    @ApiResponse(responseCode="400",description = "Datos del producto invalidos")
    @PostMapping
    public ResponseEntity<Producto>
    crearProducto(@Valid @RequestBody Producto producto) {

        Producto nuevoProducto = productoService.crearProducto(producto);

        if (nuevoProducto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // Listar todos
    @Operation(summary ="Obtener todos los productos",description = "Endpoint permite consultar todos los productos")
    @ApiResponse(responseCode="200",description = "Consulta exitosa, se entrega la lista de productos")
    @ApiResponse(responseCode="204",description = "Consulta exitosa, pero no se encontraron datos")
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
    @Operation(summary ="Obtener producto por id",description = "Endpoint permite consultar un producto mediante su identificador")
    @ApiResponse(responseCode="200",description = "Producto encontrado correctamente")
    @ApiResponse(responseCode="404",description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Producto>
    obtenerPorId(
            @Parameter(description = "ID del producto a buscar")
            @PathVariable Integer id) {

        Producto producto =
                productoService.obtenerPorId(id);

        if (producto == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Buscar por pokemon
    @Operation(summary ="Obtener productos por pokemon",description = "Endpoint permite consultar productos asociados a un pokemon")
    @ApiResponse(responseCode="200",description = "Consulta exitosa, se entrega la lista de productos")
    @ApiResponse(responseCode="204",description = "No existen productos asociados al pokemon")
    @GetMapping("/pokemon/{idPokemon}")
    public ResponseEntity<List<Producto>> listarPorPokemon(
            @Parameter(description = "ID del pokemon asociado")
            @PathVariable Integer idPokemon) {

        List<Producto> productos =
                productoService.listarPorPokemon(idPokemon);

        if (productos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar por clasificacion
    @Operation(summary ="Obtener productos por clasificacion",description = "Endpoint permite consultar productos según su clasificación")
    @ApiResponse(responseCode="200",description = "Consulta exitosa, se entrega la lista de productos")
    @ApiResponse(responseCode="204",description = "No existen productos para la clasificación indicada")
    @GetMapping("/clasificacion/{idClasificacion}")
    public ResponseEntity<List<Producto>> listarPorClasificacion(
            @Parameter(description = "ID de la clasificación")
            @PathVariable Integer idClasificacion) {

        List<Producto> productos = productoService.listarPorClasificacion(idClasificacion);

        if (productos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Actualizar
    @Operation(summary ="Actualizar producto",description = "Endpoint permite modificar los datos de un producto existente")
    @ApiResponse(responseCode="200",description = "Producto actualizado correctamente")
    @ApiResponse(responseCode="404",description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Producto>
    actualizarProducto(
            @Parameter(description = "ID del producto a actualizar")
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
    @Operation(summary ="Eliminar producto",description = "Endpoint permite eliminar un producto mediante su identificador")
    @ApiResponse(responseCode="200",description = "Producto eliminado correctamente")
    @ApiResponse(responseCode="404",description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar")
            @PathVariable Integer id) {

        boolean eliminado = productoService.eliminarProducto(id);

        if (!eliminado) {

            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
    }
}