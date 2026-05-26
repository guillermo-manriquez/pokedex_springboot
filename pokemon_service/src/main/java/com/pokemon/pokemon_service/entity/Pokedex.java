package com.pokemon.pokemon_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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