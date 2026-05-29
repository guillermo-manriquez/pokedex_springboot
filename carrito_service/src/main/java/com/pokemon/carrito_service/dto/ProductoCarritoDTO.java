package com.pokemon.carrito_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoCarritoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal precio;
}
