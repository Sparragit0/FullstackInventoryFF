package com.fragancefantasy.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjustarStockRequest {
    private Long productoId;
    private Integer cambioCantidad;
}
//endpoint de post request