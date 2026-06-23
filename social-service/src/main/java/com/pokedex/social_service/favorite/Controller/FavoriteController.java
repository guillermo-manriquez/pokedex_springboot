package com.pokedex.social_service.favorite.Controller;

import com.pokedex.social_service.favorite.Dto.FavoriteResponse;
import com.pokedex.social_service.favorite.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("api/v1/favorites")
@RequiredArgsConstructor
@Tag(name="API Favoritos",description = "API para la gestion de favoritos")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(summary ="Agregar favorito",description = "Endpoint permite agregar un pokemon a favoritos")
    @ApiResponse(responseCode="200",description = "Pokemon agregado a favoritos correctamente")
    @PostMapping("/{pokemonId}")
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestHeader("X-User-Id") Integer userId,
            @Parameter(description = "ID del pokemon")
            @PathVariable Integer pokemonId) {
        return ResponseEntity.ok(favoriteService.addFavorite(userId, pokemonId));
    }

    @Operation(summary ="Eliminar favorito",description = "Endpoint permite eliminar un pokemon de favoritos")
    @ApiResponse(responseCode="200",description = "Pokemon eliminado de favoritos correctamente")
    @ApiResponse(responseCode="404",description = "Favorito no encontrado")
    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> removeFavorite(
            @RequestHeader("X-User-Id") Integer userId,
            @Parameter(description = "ID del pokemon")
            @PathVariable Integer pokemonId) {
        favoriteService.removeFavorite(userId, pokemonId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary ="Obtener mis favoritos",description = "Endpoint permite consultar todos los pokemon favoritos de un usuario")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la lista de favoritos")
    @ApiResponse(responseCode="204",description = "Consulta exitosa , pero no se encontraron datos")
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteResponse>> getMyFavorites(
            @RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(favoriteService.getMyFavorites(userId));
    }

}
