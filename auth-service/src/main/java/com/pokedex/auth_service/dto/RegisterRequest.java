package com.pokedex.auth_service.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Se necesita username")
    private String username;
    @Email(message = "Email inválido")
    @NotBlank(message = "Se necesite email")
    private String email;

    @NotBlank
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
