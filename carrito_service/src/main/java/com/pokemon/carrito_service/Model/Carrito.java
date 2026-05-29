package com.pokemon.carrito_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Carrito")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrito;

    private Long idUsuario;

    private LocalDateTime fechaCreacion;
}