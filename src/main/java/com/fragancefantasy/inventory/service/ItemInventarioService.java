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
        ItemInventario item = itemInventarioRepository.findByProductoId(productoId)
            .orElseThrow(() -> new NoSuchElementException("No existe inventario de:" + productoId));
        return toStockResponse(item);
    }

    // POST: ajustar stock (entrada/salida)
    @Transactional
    public StockResponse ajustarStock(AjustarStockRequest request) {
        ItemInventario item = itemInventarioRepository.findByProductoId(request.getProductoId())
                .orElseGet(() -> {
                    ItemInventario itemNuevo = new ItemInventario();
                    itemNuevo.setProductoId(request.getProductoId());
                    // cantidad, stockMinimo y estado se inicializan en @PrePersist si son null
                    return itemNuevo;
                });

        int cantidadActual = item.getCantidad() != null ? item.getCantidad() : 0;
        int cambio = request.getCambioCantidad() != null ? request.getCambioCantidad() : 0;
        int nuevaCantidad = cantidadActual + cambio;

        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede quedar negativo para el producto " + request.getProductoId());
        }

        item.setCantidad(nuevaCantidad);

        // Lógica de estado según cantidad y stockMinimo
        int stockMinimo = item.getStockMinimo() != null ? item.getStockMinimo() : 0;

        if (nuevaCantidad == 0) {
            item.setEstado("SIN_STOCK");
        } else if (nuevaCantidad <= stockMinimo) {
            item.setEstado("STOCK_BAJO");
        } else {
            item.setEstado("ACTIVO");
        }

        ItemInventario itemActualizado = itemInventarioRepository.save(item);
        return toStockResponse(itemActualizado);
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

    /*// Post v1
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
    }*/
}
