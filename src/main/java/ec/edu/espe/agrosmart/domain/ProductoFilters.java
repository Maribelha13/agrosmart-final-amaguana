package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class ProductoFilters {

    // Filtra productos que tengan stock disponible mayor a cero
    public static final Predicate<Producto> CON_STOCK = producto ->
            producto.getStock() != null && producto.getStock() > 0;

    // Filtra productos cuyo precio sea menor o igual a un límite máximo
    public static Predicate<Producto> precioMenorA(BigDecimal limite) {
        return producto -> producto.getPrecio() != null && producto.getPrecio().compareTo(limite) <= 0;
    }
}