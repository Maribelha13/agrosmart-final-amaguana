package ec.edu.espe.agrosmart.dto;

import ec.edu.espe.agrosmart.entity.ProductoEntity;

import java.math.BigDecimal;

public record ProductoDTO(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer stock,
        String descripcion
) {
    public static ProductoDTO fromEntity(ProductoEntity entity) {
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