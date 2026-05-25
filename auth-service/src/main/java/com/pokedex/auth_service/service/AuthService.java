package com.pokedex.auth_service.service;

import com.pokedex.auth_service.dto.AuthResponse;
import com.pokedex.auth_service.dto.LoginRequest;
import com.pokedex.auth_service.dto.RegisterRequest;
import com.pokedex.auth_service.entity.User;
import com.pokedex.auth_service.repository.UserRepository;
import com.pokedex.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class AuthService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    @Value("${services.user-service}")
    private String userServiceUrl;

    @Value("${services.notification-service}")
    private String notificationServiceUrl;

    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        Map<String, Object> profileRequest = Map.of(
                "userId", user.getId(),
                "username", user.getUsername()
        );
        restTemplate.postForObject(
                userServiceUrl + "/api/v1/users/profile",
                profileRequest,
                Object.class
        );
        Map<String, Object> notification = Map.of(
                "userId", user.getId(),
                "message", "Bienvenido a PokedexShop " + user.getUsername() + "!",
                "type", "WELCOME"
        );
        restTemplate.postForObject(
                notificationServiceUrl + "/api/v1/notificacion/internal/create",
                notification,
                Object.class
        );

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username no existe"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        throw new RuntimeException("Credenciales no validas");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new  AuthResponse(token, user.getUsername(), user.getRole().name());
    }



}
