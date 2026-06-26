package com.pokedex.notificacion_service.Dto;


import lombok.Data;

@Data
public class NotificacionRequest {
    private Long userId;
    private String message;
    private String type;
}
