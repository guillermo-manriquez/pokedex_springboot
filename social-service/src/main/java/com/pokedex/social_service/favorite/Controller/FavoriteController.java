package com.pokedex.social_service.favorite.Controller;


import com.pokedex.social_service.favorite.Dto.FavoriteResponse;
import com.pokedex.social_service.favorite.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{pokemonId}")
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Integer pokemonId) {
        return ResponseEntity.ok(favoriteService.addFavorite(userId, pokemonId));
    }

    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> removeFavorite(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Integer pokemonId) {
        favoriteService.removeFavorite(userId, pokemonId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<FavoriteResponse>> getMyFavorites(
            @RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(favoriteService.getMyFavorites(userId));
    }

}
