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
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
        if (this.cantidad == null) {
            this.cantidad = 0;
        }
        if (this.stockMinimo == null) {
            this.stockMinimo = 0;
        }
    }

    @PreUpdate
    private void alActualizar() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
//Hace falta alterar las columnas nulables y/o unicas
