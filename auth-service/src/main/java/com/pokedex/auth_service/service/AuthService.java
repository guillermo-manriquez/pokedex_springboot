package com.pokedex.auth_service.service;

import com.pokedex.auth_service.client.CreateProfileRequest;
import com.pokedex.auth_service.client.NotificacionRequest;
import com.pokedex.auth_service.client.NotificacionServiceClient;
import com.pokedex.auth_service.client.UserServiceClient;
import com.pokedex.auth_service.dto.AuthResponse;
import com.pokedex.auth_service.dto.LoginRequest;
import com.pokedex.auth_service.dto.RegisterRequest;
import com.pokedex.auth_service.entity.User;
import com.pokedex.auth_service.repository.UserRepository;
import com.pokedex.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;
    private final NotificacionServiceClient notificacionServiceClient;


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

        userServiceClient.createProfile(
                new CreateProfileRequest(user.getId(), user.getUsername())
        );

        notificacionServiceClient.createNotificacion(
                new NotificacionRequest(
                        user.getId(),
                        "Bienvenido a PokedexShop " + user.getUsername() + "!",
                        "WELCOME"
                )
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
