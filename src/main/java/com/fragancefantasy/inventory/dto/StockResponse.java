package com.fragancefantasy.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {
    private Long productoId;
    private Integer cantidad;
    private Integer stockMinimo;
    private String estado;
    
}
//endpoint de get request