package com.pokedex.social_service.favorite.Service;


import com.pokedex.social_service.favorite.Dto.FavoriteResponse;
import com.pokedex.social_service.favorite.Entity.Favorite;
import com.pokedex.social_service.favorite.Repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteResponse addFavorite(Integer userId, Integer pokemonId) {
        if (favoriteRepository.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new RuntimeException("Pokemon ya está en favoritos");
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setPokemonId(pokemonId);
        favoriteRepository.save(favorite);

        return new FavoriteResponse(
                favorite.getId(),
                favorite.getPokemonId(),
                favorite.getCreatedAt()
        );
    }

    public List<FavoriteResponse> getMyFavorites(Integer userId) {
        return favoriteRepository.findByUserId(userId)
                .stream()
                .map(f -> new FavoriteResponse(
                        f.getId(),
                        f.getPokemonId(),
                        f.getCreatedAt()
                ))
                .toList();
    }

    @Transactional
    public void removeFavorite(Integer userId, Integer pokemonId) {
        if (!favoriteRepository.existsByUserIdAndPokemonId(userId, pokemonId)) {
            throw new RuntimeException("Pokemon no está en favoritos");
        }
        favoriteRepository.deleteByUserIdAndPokemonId(userId, pokemonId);
    }
}
