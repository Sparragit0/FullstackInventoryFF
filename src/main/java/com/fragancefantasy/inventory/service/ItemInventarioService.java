package com.fragancefantasy.inventory.service;

import org.springframework.stereotype.Service;

import com.fragancefantasy.inventory.dto.AjustarStockRequest;
import com.fragancefantasy.inventory.dto.StockResponse;
import com.fragancefantasy.inventory.model.ItemInventario;
import com.fragancefantasy.inventory.repository.ItemInventarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemInventarioService {
    private final ItemInventarioRepository itemInventarioRepository;

    public ItemInventarioService(ItemInventarioRepository itemInventarioRepository) {
        this.itemInventarioRepository = itemInventarioRepository;
    }

    @Transactional
    public StockResponse obtenerStockPorProductoid(Long productoid) {
        return null;
    }

    @Transactional
    public StockResponse ajustarStock(AjustarStockRequest request) {
        return null;
    }

    // Transforma ItemInventario en DTO para mantener encapsulamiento
    private StockResponse toStockResponse(ItemInventario item) {
        return new StockResponse(
            item.getProductoId(),
            item.getCantidad(),
            item.getStockMinimo(),
            item.getEstado()
        );
    }
}
