package com.pokemon.orden_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoResponse {
    private Long paymentId;
    private Integer orderId;
    private BigDecimal amount;
    private String status;
    private String message;
}
