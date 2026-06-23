package com.pokedex.pago_service.Controller;


import com.pokedex.pago_service.Dto.PagoRequest;
import com.pokedex.pago_service.Dto.PagoResponse;
import com.pokedex.pago_service.Service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/v1/pago")
@RequiredArgsConstructor
@Tag(name="API Pago",description = "API para la gestion de pagos")
public class PagoController {

    private final PagoService paymentService;

    @Operation(summary ="Procesar pago",description = "Endpoint permite procesar un pago")
    @ApiResponse(responseCode="200",description = "Pago procesado correctamente")
    @ApiResponse(responseCode="400",description = "Solicitud de pago invalida")
    @PostMapping("/process")
    public ResponseEntity<PagoResponse> processPayment(
            @Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @Operation(summary ="Obtener pago por orden",description = "Endpoint permite consultar un pago mediante el identificador de la orden")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del pago")
    @ApiResponse(responseCode="404",description = "Pago no encontrado")
    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<PagoResponse> getPaymentByOrderId(
            @Parameter(description = "ID de la orden")
            @PathVariable Integer ordenId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(ordenId));
    }

}