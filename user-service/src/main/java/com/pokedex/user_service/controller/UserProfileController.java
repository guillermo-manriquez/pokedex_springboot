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

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(userProfileService.getMyProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateMyProfile(authentication.getName(), request));
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> createProfile(@RequestBody CreateProfileRequest request) {
        return ResponseEntity.ok(userProfileService.createProfile(request));
    }

    //agregado para usar openfeing con collection-service
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> obtenerUsuarioPorId(@PathVariable Long id) {
        UserProfile usuario = userProfileService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
