package com.pokemon.orden_service.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequest {
    private Integer orderId;
    private Integer userId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String cardNumber;

}
