package com.fragancefantasy.inventory.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "item_inventario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;

    private Integer cantidad;

    private Integer stockMinimo;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private String estado;

    @PrePersist
    public void alCrear() {

    }

    @PreUpdate
    private void alActualizar() {

    }

}
