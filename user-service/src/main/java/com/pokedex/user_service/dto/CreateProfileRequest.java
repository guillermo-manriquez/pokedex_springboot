package com.pokedex.user_service.dto;

import lombok.Data;

@Data
public class CreateProfileRequest {
    private Long userId;
    private String username;
}
