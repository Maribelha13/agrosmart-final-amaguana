package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.function.Function;

public final class ProductoFunctions {

    // Función pura para convertir el nombre a mayúsculas
    public static final Function<Producto, Producto> A_MAYUSCULAS = producto -> new Producto(
            producto.getId(),
            producto.getNombre().toUpperCase(),
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getStock(),
            producto.getCorreosNotificacion()
    );

    // Función pura para aplicar un descuento del 10%
    public static final Function<Producto, Producto> APLICAR_DESCUENTO = producto -> new Producto(
            producto.getId(),
            producto.getNombre(),
            producto.getCategoria(),
            producto.getPrecio().multiply(new BigDecimal("0.90")),
            producto.getStock(),
            producto.getCorreosNotificacion()
    );
}