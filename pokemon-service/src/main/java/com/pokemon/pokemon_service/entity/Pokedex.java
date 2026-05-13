package com.pokemon.pokemon_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pokedex")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pokedex {

    @Id
    @Column(name = "id_pokedex")
    private Integer idPokedex;

    @Column(name = "nombre_pokedex")
    private String nombrePokedex;

    @Column(name = "generacion")
    private String generacion;
}