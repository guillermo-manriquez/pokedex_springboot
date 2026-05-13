package com.pokemon.coleccion_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "lista_pokemon_detalle")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaPokemonDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    private Integer idLista;

    private Integer idPokemon;

    private LocalDateTime fechaAgregado;
}
