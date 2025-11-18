package com.fragancefantasy.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fragancefantasy.inventory.model.ItemInventario;

public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long>{ 
    // Productoid conecta con el micro de catálogo
    // Optional porque puede que no exista el producto aún
    Optional<ItemInventario> findByProductoId(Long productoid);
    
}
