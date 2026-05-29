package com.pokemon.orden_service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private Long userId;
    private String username;
    private String displayName;
}
