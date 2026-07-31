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
        if (entity == null) return null;
        return new ProductoDTO(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getCategoria(),
                entity.getPrecioUsd(),
                entity.getStockKg(),
                entity.getDescripcion()
        );
    }
}