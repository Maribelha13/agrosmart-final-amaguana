package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductoFunctionsTest {

    @Test
    void testAMayusculas() {
        // Arrange
        Producto productoOriginal = new Producto(
                1L,
                "fertilizante organico",
                "Agricultura",
                new BigDecimal("25.50"),
                100,
                List.of("admin@agrosmart.com")
        );

        // Act
        Producto productoModificado = ProductoFunctions.A_MAYUSCULAS.apply(productoOriginal);

        // Assert
        assertEquals("FERTILIZANTE ORGANICO", productoModificado.getNombre());
        // Verificamos que el objeto original no haya cambiado (Inmutabilidad)
        assertEquals("fertilizante organico", productoOriginal.getNombre());
    }

    @Test
    void testAplicarDescuento() {
        // Arrange
        Producto productoOriginal = new Producto(
                2L,
                "Semilla de Maíz",
                "Agricultura",
                new BigDecimal("100.00"),
                50,
                List.of("ventas@agrosmart.com")
        );

        // Act
        Producto productoModificado = ProductoFunctions.APLICAR_DESCUENTO.apply(productoOriginal);

        // Assert
        // 100.00 con 10% de descuento debe ser 90.00
        assertEquals(0, new BigDecimal("90.00").compareTo(productoModificado.getPrecio()));
        assertEquals(new BigDecimal("100.00"), productoOriginal.getPrecio()); // Inmutabilidad validada
    }
}