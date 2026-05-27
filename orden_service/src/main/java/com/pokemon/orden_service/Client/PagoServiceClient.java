package com.pokemon.orden_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pago-service")
public interface PagoServiceClient {

    @PostMapping("/api/v1/pago/process")
    PagoResponse procesarPago(@RequestBody PagoRequest request);
}
