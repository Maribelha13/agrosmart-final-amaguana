package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.List;

public class Producto {
    private final Long id;
    private final String nombre;
    private final String categoria;
    private final BigDecimal precio;
    private final Integer stock;
    private final List<String> correosNotificacion;

    public Producto(Long id, String nombre, String categoria, BigDecimal precio, Integer stock, List<String> correosNotificacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        // Copia defensiva en el constructor para inmutabilidad
        this.correosNotificacion = correosNotificacion != null ? List.copyOf(correosNotificacion) : List.of();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    // Copia defensiva en el getter para inmutabilidad
    public List<String> getCorreosNotificacion() {
        return List.copyOf(correosNotificacion);
    }
}