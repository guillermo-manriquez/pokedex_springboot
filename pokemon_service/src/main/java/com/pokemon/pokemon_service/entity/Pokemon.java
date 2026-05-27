package com.pokemon.pokemon_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pokemon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pokemon {
    @Id
    private Integer idPokemon;

    @ManyToOne
    @JoinColumn(name = "id_pokedex")
    private Pokedex pokedex;

    @NotNull
    private String nombre;

    @Column(length = 1000)
    private String imagenUrl;

    @Column(length = 500)
    private String descripcion;

    private Integer altura;

    private Integer peso;

    @NotNull
    private Integer hp;

    @NotNull
    private Integer ataque;

    @NotNull
    private Integer defensa;

    @NotNull
    private Integer velocidad;

    @NotNull
    private Integer ataqueSp;

    @NotNull
    private Integer defensaSp;

    private Integer experienciaBase;

    private String forma;

    @NotNull
    private String tipo1;

    private String tipo2;

    @NotNull
    private String habilidad1;

    private String habilidad2;

    private String habilidadOculta;

}