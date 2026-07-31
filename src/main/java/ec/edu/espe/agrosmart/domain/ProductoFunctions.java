package ec.edu.espe.agrosmart.domain;

import java.util.function.Function;

public class ProductoFunctions {

    public static final Function<Producto, Producto> A_MAYUSCULAS = producto ->
            new Producto(
                    producto.getId(),
                    producto.getNombre().toUpperCase(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getCorreosNotificacion()
            );
}