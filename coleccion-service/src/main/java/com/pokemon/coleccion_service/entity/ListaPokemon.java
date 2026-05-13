package com.pokemon.coleccion_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lista_pokemon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLista;

    private Integer idUsuario;

    @NotBlank
    private String nombreLista;

    private String descripcion;

    private LocalDateTime fechaCreacion;
}
