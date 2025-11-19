package com.fragancefantasy.inventory.service;

import java.util.NoSuchElementException;

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

    // Get
    @Transactional
    public StockResponse obtenerStockPorProductoId(Long productoId) {
        ItemInventario item = itemInventarioRepository.findByProductoId(productoId).orElseThrow(() -> new NoSuchElementException("No existe inventario de:" + productoId));
        return toStockResponse(item);
    }

    // 
    @Transactional
    public StockResponse ajustarStock(AjustarStockRequest request) {
        ItemInventario item = itemInventarioRepository.findByProductoId(request.getProductoId()).orElseGet(() ->{
            ItemInventario itemNuevo = new ItemInventario();
            itemNuevo.setProductoId(request.getProductoId());
            itemNuevo.setCantidad(0);
            itemNuevo.setStockMinimo(0);
            itemNuevo.setEstado("ACTIVO");
            return itemNuevo;
        });
        int nuevaCantidad = item.getCantidad() + request.getCambioCantidad();
       
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("El stock no puede quedar negativo");
        }

        item.setCantidad(nuevaCantidad);

        // Manejo de "item.estado"
        if (nuevaCantidad == 0) {
            item.setEstado("SIN_STOCK");
        } else {
            item.setEstado("ACTIVO");
        }


        ItemInventario ItemActualizado = itemInventarioRepository.save(item);
        return toStockResponse(ItemActualizado);
    }

    // Verifica si existe inventario por productoId
    public boolean existInventarioPorProductoId(Long productoId) {
        return itemInventarioRepository.findByProductoId(productoId).isPresent();
    }

    // Transforma ItemInventario en DTO StockResponse 
    private StockResponse toStockResponse(ItemInventario item) {
        return new StockResponse(
            item.getProductoId(),
            item.getCantidad(),
            item.getStockMinimo(),
            item.getEstado()
        );
    }
}
