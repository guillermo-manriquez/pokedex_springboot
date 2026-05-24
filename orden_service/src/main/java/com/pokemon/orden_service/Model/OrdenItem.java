package com.pokemon.orden_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Orden_Item")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdenItem;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;

    private Integer idProducto;

    private Integer cantidad;

    private BigDecimal precioHistorico;
}