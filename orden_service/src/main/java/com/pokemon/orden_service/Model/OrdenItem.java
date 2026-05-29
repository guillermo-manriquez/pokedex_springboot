package com.pokemon.orden_service.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orden_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_item")
    private Integer idOrdenItem;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_historico", nullable = false)
    private BigDecimal precioHistorico;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    @JsonBackReference
    private Orden orden;
}