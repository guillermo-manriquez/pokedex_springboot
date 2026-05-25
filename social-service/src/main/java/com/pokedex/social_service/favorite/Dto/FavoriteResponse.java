package com.pokedex.social_service.favorite.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FavoriteResponse {
    private Long id;
    private Integer pokemonId;
    private LocalDateTime createdAt;
}
