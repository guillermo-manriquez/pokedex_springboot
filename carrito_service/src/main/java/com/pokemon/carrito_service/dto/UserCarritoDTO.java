package com.pokemon.carrito_service.dto;

import lombok.Data;

@Data
public class UserCarritoDTO {
    private Long id;
    private Long userId;
    private String username;
    private String displayName;
}
