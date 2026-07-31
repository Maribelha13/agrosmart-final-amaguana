package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List; // 1. Asegúrate de importar List

import static org.assertj.core.api.Assertions.assertThat;

class ProductoTest {

    @Test
    @DisplayName("La función A_MAYUSCULAS no debe mutar el producto original y retornar uno nuevo")
    void funcionAMayusculasDebeSerPura() {
        // Arrange
        Producto productoOriginal = new Producto(
                1L,
                "banano cavendish premium",
                "Banano",
                new BigDecimal("10.00"),
                80,
                List.of("etiqueta1") // 2. Reemplaza el 'true' por una lista válida
        );

        // Act: Aplicar función pura A_MAYUSCULAS
        Producto productoTransformado = ProductoFunctions.A_MAYUSCULAS.apply(productoOriginal);

        // Assert: El objeto original no cambia, el nuevo tiene el nombre en mayúsculas
        assertThat(productoOriginal.getNombre()).isEqualTo("banano cavendish premium");
        assertThat(productoTransformado.getNombre()).isEqualTo("BANANO CAVENDISH PREMIUM");
        assertThat(productoTransformado).isNotSameAs(productoOriginal);
    }
}