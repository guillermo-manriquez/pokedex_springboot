package com.pokedex.pago_service.client;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionRequest {
    private Integer userId;
    private String message;
    private String type;
}
