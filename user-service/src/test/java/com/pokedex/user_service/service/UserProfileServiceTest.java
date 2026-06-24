package com.pokedex.user_service.service;

import com.pokedex.user_service.dto.CreateProfileRequest;
import com.pokedex.user_service.dto.UpdateProfileRequest;
import com.pokedex.user_service.dto.UserProfileResponse;
import com.pokedex.user_service.entity.UserProfile;
import com.pokedex.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    void getMyProfile_deberiaRetornarPerfil() {

        // Arrange

        UserProfile profile = new UserProfile();
        profile.setUserId(1L);
        profile.setUsername("guillermo");
        profile.setDisplayName("Guillermo");

        when(userRepository.findByUsername("guillermo"))
                .thenReturn(Optional.of(profile));

        // Act

        UserProfileResponse response =
                userProfileService.getMyProfile("guillermo");

        // Assert

        assertEquals(1L, response.getUserId());
        assertEquals("guillermo", response.getUsername());
        assertEquals("Guillermo", response.getDisplayName());

        verify(userRepository)
                .findByUsername("guillermo");
    }

    @Test
    void getMyProfile_deberiaLanzarExcepcionSiNoExiste() {

        // Arrange

        when(userRepository.findByUsername("guillermo"))
                .thenReturn(Optional.empty());

        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userProfileService.getMyProfile("guillermo")
        );

        assertEquals(
                "Profile not found",
                exception.getMessage()
        );

        verify(userRepository)
                .findByUsername("guillermo");
    }

    @Test
    void createProfile_deberiaCrearPerfil() {

        // Arrange

        CreateProfileRequest request = new CreateProfileRequest();
        request.setUserId(1L);
        request.setUsername("guillermo");

        when(userRepository.existsByUserId(1L))
                .thenReturn(false);

        // Act

        UserProfileResponse response =
                userProfileService.createProfile(request);

        // Assert

        assertEquals(1L, response.getUserId());
        assertEquals("guillermo", response.getUsername());

        verify(userRepository)
                .existsByUserId(1L);

        verify(userRepository)
                .save(any(UserProfile.class));
    }

    @Test
    void updateMyProfile_deberiaActualizarDisplayName() {

        // Arrange

        UserProfile profile = new UserProfile();
        profile.setUserId(1L);
        profile.setUsername("guillermo");
        profile.setDisplayName("ViejoNombre");

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setDisplayName("NuevoNombre");

        when(userRepository.findByUsername("guillermo"))
                .thenReturn(Optional.of(profile));

        // Act

        UserProfileResponse response =
                userProfileService.updateMyProfile("guillermo", request);

        // Assert

        assertEquals("NuevoNombre", response.getDisplayName());

        verify(userRepository)
                .findByUsername("guillermo");

        verify(userRepository)
                .save(profile);
    }

}