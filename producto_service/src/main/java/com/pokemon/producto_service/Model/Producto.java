package com.pokemon.producto_service.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @ManyToOne
    @JoinColumn(name = "id_clasificacion")
    private ClasificacionProducto clasificacionProducto;

    private Integer idPokemon;

    @NotBlank
    private String nombreProducto;

    private String descripcion;

    @NotNull
    private BigDecimal precio;

    private Integer stock;
}