package com.pokedex.notificacion_service.Dto;


import lombok.Data;

@Data
public class NotificacionRequest {
    private Integer userId;
    private String message;
    private String type;
}
