package com.pokemon.producto_service.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clasificacion_producto")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClasificacionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClasificacion;

    @NotBlank
    private String nombreCategoria;
}