package com.fragancefantasy.inventory.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fragancefantasy.inventory.dto.AjustarStockRequest;
import com.fragancefantasy.inventory.dto.StockResponse;
import com.fragancefantasy.inventory.service.ItemInventarioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ItemInventarioController {
    private final ItemInventarioService itemInventarioService;
    
    public ItemInventarioController(ItemInventarioService itemInventarioService) {
        this.itemInventarioService = itemInventarioService;
    }

    // Get stock por productoId
    @GetMapping("/{productoId}")
    public ResponseEntity<StockResponse> obtenerStock (@PathVariable Long productoId) {
        StockResponse response = itemInventarioService.obtenerStockPorProductoId(productoId);
        return ResponseEntity.ok(response);
    }
    
    // Post ajuste de stock
    @PostMapping("/ajustar-stock")
    public ResponseEntity<StockResponse> ajustarStock (@Valid @RequestBody AjustarStockRequest request) {
        boolean existe = itemInventarioService.existInventarioPorProductoId(request.getProductoId());
        StockResponse response = itemInventarioService.ajustarStock(request);

        if (!existe) {
            return ResponseEntity.status(201).body(response);
        }
        
        return ResponseEntity.ok(response);
    }

}   
