package com.pokedex.social_service.favorite.Repository;

import com.pokedex.social_service.favorite.Entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Integer userId);
    Optional<Favorite> findByUserIdAndPokemonId(Integer userId, Integer pokemonId);
    boolean existsByUserIdAndPokemonId(Integer userId, Integer pokemonId);
    void deleteByUserIdAndPokemonId(Integer userId, Integer pokemonId);
}
