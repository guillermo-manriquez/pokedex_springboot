package com.pokedex.user_service.controller;

import com.pokedex.user_service.dto.CreateProfileRequest;
import com.pokedex.user_service.dto.UpdateProfileRequest;
import com.pokedex.user_service.dto.UserProfileResponse;

import com.pokedex.user_service.entity.UserProfile;

import com.pokedex.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name="API Usuario",description = "API para la gestion de usuarios")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary ="Obtener mi perfil",description = "Endpoint permite consultar el perfil del usuario autenticado")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del perfil")
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(userProfileService.getMyProfile(authentication.getName()));

    }

    @Operation(summary ="Actualizar mi perfil",description = "Endpoint permite actualizar la informacion del usuario autenticado")
    @ApiResponse(responseCode="200",description = "Perfil actualizado correctamente")
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,

            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(userProfileService.updateMyProfile(authentication.getName(), request));

    }

    @Operation(summary ="Crear perfil",description = "Endpoint permite crear un perfil de usuario")
    @ApiResponse(responseCode="200",description = "Perfil creado correctamente")
    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> createProfile(@RequestBody CreateProfileRequest request) {

        return ResponseEntity.ok(userProfileService.createProfile(request));

    }

//agregado para usar openfeing con collection-service

    @Operation(summary ="Obtener usuario por id",description = "Endpoint permite consultar un usuario mediante su identificador")
    @ApiResponse(responseCode="200",description = "Consulta exitosa , se entrega la informacion del usuario")
    @ApiResponse(responseCode="404",description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> obtenerUsuarioPorId(@Parameter(description = "ID del usuario") @PathVariable Long id) {

        UserProfile usuario = userProfileService.obtenerPorId(id);

        return ResponseEntity.ok(usuario);

    }

}
