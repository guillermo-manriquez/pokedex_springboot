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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class AuthService {

    private final Logger log  = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;
    private final NotificacionServiceClient notificacionServiceClient;


    public AuthResponse register(RegisterRequest request){
        log.info("Intentando registrar usuario: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())){
            log.warn("Usuario ya existe: {}", request.getUsername());
            throw new RuntimeException("Username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            log.warn("Email ya existe: {}", request.getEmail());
            throw new RuntimeException("Email ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        log.info("Usuario registrado exitosamente con id: {}", user.getId());

        userServiceClient.createProfile(
                new CreateProfileRequest(user.getId(), user.getUsername())
        );
        log.info("Perfil creado exitosamente en user-service para usuario: {}", user.getUsername());

        notificacionServiceClient.createNotificacion(
                new NotificacionRequest(
                        user.getId(),
                        "Bienvenido a PokedexShop " + user.getUsername() + "!",
                        "WELCOME"
                )
        );
        log.info("Notificacion de bienvenida enviada para usuario: {}", user.getUsername());

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request){
        log.info("Intento de login para usuario: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->{
                    log.warn("Usuario no encontrado: {}", request.getUsername());
                    return new RuntimeException("Username no existe");
                });


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            log.warn("Credenciales invalidas para usuario: {}", request.getUsername());
            throw new RuntimeException("Credenciales no validas");
        }

        log.info("Login exitoso para usuario: {}", request.getUsername());
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new  AuthResponse(token, user.getUsername(), user.getRole().name());
    }



}
