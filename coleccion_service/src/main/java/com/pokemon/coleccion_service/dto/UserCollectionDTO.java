package com.pokemon.coleccion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCollectionDTO {
    private Long id;
    private Long userId;
    private String username;
    private String displayName;
}
