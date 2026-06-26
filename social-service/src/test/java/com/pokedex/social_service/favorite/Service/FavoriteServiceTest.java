package com.pokedex.social_service.favorite.Service;

import com.pokedex.social_service.favorite.Dto.FavoriteResponse;
import com.pokedex.social_service.favorite.Entity.Favorite;
import com.pokedex.social_service.favorite.Repository.FavoriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    void addFavorite_deberiaAgregarFavorito() {

        // Arrange

        when(favoriteRepository.existsByUserIdAndPokemonId(1, 25))
                .thenReturn(false);

        // Act

        FavoriteResponse response = favoriteService.addFavorite(1, 25);

        // Assert

        assertEquals(25, response.getPokemonId());

        verify(favoriteRepository)
                .save(any(Favorite.class));
    }

    @Test
    void addFavorite_deberiaLanzarExcepcionSiYaExiste() {

        // Arrange

        when(favoriteRepository.existsByUserIdAndPokemonId(1, 25))
                .thenReturn(true);

        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> favoriteService.addFavorite(1, 25)
        );

        assertEquals(
                "Pokemon ya está en favoritos",
                exception.getMessage()
        );

        verify(favoriteRepository, never())
                .save(any());
    }

    @Test
    void getMyFavorites_deberiaRetornarLista() {

        // Arrange

        Favorite favorite = new Favorite();
        favorite.setPokemonId(25);

        when(favoriteRepository.findByUserId(1))
                .thenReturn(List.of(favorite));

        // Act

        List<FavoriteResponse> response =
                favoriteService.getMyFavorites(1);

        // Assert

        assertEquals(1, response.size());
        assertEquals(25, response.get(0).getPokemonId());

        verify(favoriteRepository)
                .findByUserId(1);
    }

    @Test
    void removeFavorite_deberiaEliminarFavorito() {

        // Arrange

        when(favoriteRepository.existsByUserIdAndPokemonId(1, 25))
                .thenReturn(true);

        // Act

        favoriteService.removeFavorite(1, 25);

        // Assert

        verify(favoriteRepository)
                .deleteByUserIdAndPokemonId(1, 25);
    }

}