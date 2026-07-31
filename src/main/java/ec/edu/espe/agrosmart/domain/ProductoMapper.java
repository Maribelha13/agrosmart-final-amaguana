package ec.edu.espe.agrosmart.domain;

import ec.edu.espe.agrosmart.entity.ProductoEntity;

public class ProductoMapper {

    public static Producto toDomain(ProductoEntity entity) {
        if (entity == null) return null;
        return new Producto(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getCategoria(),
                entity.getPrecioUsd(),
                entity.getStockKg(),
                null
        );
    }

    public static ProductoEntity toEntity(Producto domain) {
        if (domain == null) return null;
        ProductoEntity entity = new ProductoEntity();
        entity.setIdProducto(domain.getId());
        entity.setNombreProducto(domain.getNombre());
        entity.setCategoria(domain.getCategoria());
        entity.setPrecioUsd(domain.getPrecio());
        entity.setStockKg(domain.getStock());
        return entity;
    }
}