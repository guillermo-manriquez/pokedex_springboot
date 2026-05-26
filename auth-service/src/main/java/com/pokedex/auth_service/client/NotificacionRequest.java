package com.pokedex.auth_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionRequest {
    private Long userId;
    private String message;
    private String type;
}
