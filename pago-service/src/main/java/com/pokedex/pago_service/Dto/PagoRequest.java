package com.pokedex.pago_service.Dto;

import com.pokedex.pago_service.Model.Pago;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagoRequest {
    @NotNull
    private Integer orderId;

    @NotNull
    private Long userId;

    @NotNull
    @Positive
    private BigDecimal amount;

    private String currency = "USD";

    @NotNull
    private Pago.PaymentMethod paymentMethod;

    private String cardNumber;
}
