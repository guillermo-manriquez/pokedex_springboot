package com.pokedex.pago_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PagoResponse {
    private Long paymentId;
    private Integer orderId;
    private BigDecimal amount;
    private String status;
    private String message;
}
