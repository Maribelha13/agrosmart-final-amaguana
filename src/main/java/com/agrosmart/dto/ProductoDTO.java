package com.agrosmart.dto;

import java.math.BigDecimal;

public record ProductoDTO(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer stock,
        String descripcion
) {
    public static ProductoDTO fromEntity(com.agrosmart.entity.ProductoEntity entity) {
        return new ProductoDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getCategoria(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getDescripcion()
        );
    }
}