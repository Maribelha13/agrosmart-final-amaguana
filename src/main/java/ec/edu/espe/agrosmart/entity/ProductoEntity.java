package ec.edu.espe.agrosmart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio_usd")
    private BigDecimal precioUsd;

    @Column(name = "stock_kg")
    private Integer stockKg;

    @Column(name = "descripcion")
    private String descripcion;

    // Constructor vacío (obligatorio para JPA)
    public ProductoEntity() {}

    // Constructor con 6 parámetros para el DataInitializer
    public ProductoEntity(Long idProducto, String nombreProducto, String categoria, BigDecimal precioUsd, Integer stockKg, String descripcion) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.precioUsd = precioUsd;
        this.stockKg = stockKg;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPrecioUsd() { return precioUsd; }
    public void setPrecioUsd(BigDecimal precioUsd) { this.precioUsd = precioUsd; }

    public Integer getStockKg() { return stockKg; }
    public void setStockKg(Integer stockKg) { this.stockKg = stockKg; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}