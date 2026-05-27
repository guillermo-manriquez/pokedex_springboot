package com.pokedex.user_service.service;

import com.pokedex.user_service.dto.CreateProfileRequest;
import com.pokedex.user_service.dto.UpdateProfileRequest;
import com.pokedex.user_service.dto.UserProfileResponse;
import com.pokedex.user_service.entity.UserProfile;
import com.pokedex.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userProfileRepository;

    public UserProfileResponse getMyProfile(String username) {
        UserProfile profile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponse(profile);
    }

    public UserProfileResponse updateMyProfile(String username, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (request.getDisplayName() != null) profile.setDisplayName(request.getDisplayName());

        userProfileRepository.save(profile);
        return mapToResponse(profile);
    }


    private UserProfileResponse mapToResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getUserId(),
                profile.getUsername(),
                profile.getDisplayName()
        );
    }

    public UserProfileResponse createProfile(CreateProfileRequest request) {
        if (userProfileRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("Profile already exists");
        }

        UserProfile profile = new UserProfile();
        profile.setUserId(request.getUserId());
        profile.setUsername(request.getUsername());

        userProfileRepository.save(profile);
        return mapToResponse(profile);
    }

    //agregado para usar openfeing con collection-service
    public UserProfile obtenerPorId(Long id) {

        return userProfileRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
    }
}
