package com.pokedex.pago_service.client;

import com.pokedex.pago_service.Dto.OrdenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "orden-service", url = "http://localhost:8088")
public interface OrdenClient {

    @GetMapping("/api/orden/{id}")
    OrdenDTO getOrdenById(@PathVariable("id") Integer id);

    @GetMapping("/api/orden/usuario/{idUsuario}")
    List<OrdenDTO> obtenerOrdenesPorUsuario(@PathVariable("idUsuario") Long idUsuario);
}
