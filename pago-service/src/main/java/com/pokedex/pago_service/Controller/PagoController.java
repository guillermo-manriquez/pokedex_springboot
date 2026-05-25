package com.pokedex.pago_service.Controller;


import com.pokedex.pago_service.Dto.PagoRequest;
import com.pokedex.pago_service.Dto.PagoResponse;
import com.pokedex.pago_service.Service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pago")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PagoResponse> processPayment(
            @Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<PagoResponse> getPaymentByOrderId(
            @PathVariable Integer ordenId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(ordenId));
    }

}
