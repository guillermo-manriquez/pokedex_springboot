package com.pokemon.carrito_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Carrito_Item")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarritoItem;

    private Integer idCarrito;

    private Integer idProducto;

    private Integer cantidad;
}