package com.pokemon.coleccion_service.dto;

import lombok.Data;

@Data
public class PokemonCollectionDTO {
    private Integer idPokemon;
    private String nombre;
    private String tipo1;
    private String tipo2;
    private String imagenUrl;
}