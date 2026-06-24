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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private NotificacionServiceClient notificacionServiceClient;

    @InjectMocks
    private AuthService authService;

    @Test
    void pruebaMockito() {

    }

    @Test
    void login_deberiaLanzarExcepcionSiUsuarioNoExiste() {

        // Arrange (preparación)

        // Creamos un request de login ficticio
        LoginRequest request = new LoginRequest();
        request.setUsername("guillermo");
        request.setPassword("1234");

        // Cuando el repositorio busque el usuario,
        // simulamos que no existe
        when(userRepository.findByUsername("guillermo"))
                .thenReturn(Optional.empty());

        // Act + Assert

        // Ejecutamos el metodo y verificamos que lance RuntimeException
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );


        // Verificamos que el mensaje sea el esperado
        assertEquals("Username no existe", exception.getMessage());
    }
    @Test
    void login_deberiaRetornarTokenSiCredencialesSonValidas() {

        // Arrange

        LoginRequest request = new LoginRequest();
        request.setUsername("guillermo");
        request.setPassword("1234");

        User user = new User();
        user.setId(1L);
        user.setUsername("guillermo");
        user.setPassword("hash123");
        user.setRole(User.Role.USER);

        // Simulamos que el usuario existe en la BD
        when(userRepository.findByUsername("guillermo"))
                .thenReturn(Optional.of(user));

        // Simulamos que la contraseña coincide
        when(passwordEncoder.matches("1234", "hash123"))
                .thenReturn(true);

        // Simulamos la generación del JWT
        when(jwtUtil.generateToken("guillermo", "USER"))
                .thenReturn("token123");

        // Act

        AuthResponse response = authService.login(request);

        // Assert

        assertEquals("token123", response.getToken());
        assertEquals("guillermo", response.getUsername());
        assertEquals("USER", response.getRole());

        // Verificamos que los métodos fueron llamados

        verify(userRepository).findByUsername("guillermo");

        verify(passwordEncoder)
                .matches("1234", "hash123");

        verify(jwtUtil)
                .generateToken("guillermo", "USER");
    }

    @Test
    void register_deberiaRegistrarUsuarioCorrectamente() {

        // Arrange

        RegisterRequest request = new RegisterRequest();
        request.setUsername("guillermo");
        request.setEmail("guillermo@test.com");
        request.setPassword("1234");

        when(userRepository.existsByUsername("guillermo"))
                .thenReturn(false);

        when(userRepository.existsByEmail("guillermo@test.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("1234"))
                .thenReturn("hash123");

        when(jwtUtil.generateToken("guillermo", "USER"))
                .thenReturn("token123");

        // Act

        AuthResponse response = authService.register(request);

        // Assert

        assertEquals("token123", response.getToken());
        assertEquals("guillermo", response.getUsername());
        assertEquals("USER", response.getRole());

        // Verificamos que el usuario fue guardado

        verify(userRepository).save(any(User.class));

        // Verificamos que se creó el perfil

        verify(userServiceClient)
                .createProfile(any(CreateProfileRequest.class));

        // Verificamos que se envió la notificación

        verify(notificacionServiceClient)
                .createNotificacion(any(NotificacionRequest.class));

        // Verificamos generación del token

        verify(jwtUtil)
                .generateToken("guillermo", "USER");
    }
}
