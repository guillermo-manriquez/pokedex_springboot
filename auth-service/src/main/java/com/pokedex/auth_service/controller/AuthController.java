package com.pokedex.auth_service.controller;


import com.pokedex.auth_service.dto.AuthResponse;
import com.pokedex.auth_service.dto.LoginRequest;
import com.pokedex.auth_service.dto.RegisterRequest;
import com.pokedex.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name="API Auth",description = "API para la gestion de autenticacion de usuarios")
public class AuthController {

    private final AuthService authService;

    @Operation(summary ="Registrar usuario",description = "Endpoint permite registrar un nuevo usuario")
    @ApiResponse(responseCode="200",description = "Usuario registrado correctamente")
    @ApiResponse(responseCode="400",description = "Datos de registro invalidos")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary ="Iniciar sesion",description = "Endpoint permite autenticar un usuario mediante sus credenciales")
    @ApiResponse(responseCode="200",description = "Autenticacion exitosa , se entrega el token de acceso")
    @ApiResponse(responseCode="401",description = "Credenciales invalidas")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}