package ec.edu.espe.agrosmart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_productos_base_94")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean comercializable;

    public ProductoEntity() {
    }

    public ProductoEntity(Long id, String nombre, String categoria, BigDecimal precio, Integer stock, Boolean comercializable) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.comercializable = comercializable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getComercializable() { return comercializable; }
    public void setComercializable(Boolean comercializable) { this.comercializable = comercializable; }
}