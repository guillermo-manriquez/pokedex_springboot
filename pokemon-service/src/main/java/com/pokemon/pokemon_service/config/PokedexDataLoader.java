package com.pokemon.pokemon_service.config;

import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.repository.PokedexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokedexDataLoader implements CommandLineRunner {

    private final PokedexRepository pokedexRepository;

    public PokedexDataLoader(
            PokedexRepository pokedexRepository
    ) {
        this.pokedexRepository = pokedexRepository;
    }

    @Override
    public void run(String... args) {

        if (pokedexRepository.count() > 0) {
            return;
        }

        pokedexRepository.saveAll(List.of(

                new Pokedex(1, "kanto", "Gen I"),
                new Pokedex(2, "johto", "Gen II"),
                new Pokedex(3, "hoenn", "Gen III"),
                new Pokedex(4, "sinnoh", "Gen IV"),
                new Pokedex(5, "unova", "Gen V"),
                new Pokedex(6, "kalos", "Gen VI"),
                new Pokedex(7, "alola", "Gen VII"),
                new Pokedex(8, "galar", "Gen VIII"),
                new Pokedex(9, "paldea", "Gen IX"),
                new Pokedex(10, "national", "Todas")

        ));

        System.out.println("Pokedex inicializada");
    }
}