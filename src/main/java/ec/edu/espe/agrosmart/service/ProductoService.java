package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.domain.ProductoFunctions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    // Simulación de un repositorio reactivo en memoria para el ejemplo
    private final List<Producto> productosBase = List.of(
            new Producto(1L, "fertilizante organico", "Agricultura", new BigDecimal("25.50"), 100, List.of("admin@agrosmart.com")),
            new Producto(2L, "Semilla de Maíz", "Agricultura", new BigDecimal("100.00"), 50, List.of("ventas@agrosmart.com"))
    );

    // Obtener todos los productos como un Flux (flujo reactivo de 0 a N elementos)
    public Flux<Producto> listarProductos() {
        return Flux.fromIterable(productosBase);
    }

    // Obtener un producto por ID como un Mono (0 o 1 elemento)
    public Mono<Producto> obtenerPorId(Long id) {
        return Flux.fromIterable(productosBase)
                .filter(p -> p.getId().equals(id))
                .next();
    }

    // Aplicar función pura de transformación a mayúsculas de forma reactiva
    public Mono<Producto> transformarAMayusculas(Long id) {
        return obtenerPorId(id)
                .map(ProductoFunctions.A_MAYUSCULAS);
    }

    // Aplicar descuento de forma reactiva
    public Mono<Producto> aplicarDescuentoAProducto(Long id) {
        return obtenerPorId(id)
                .map(ProductoFunctions.APLICAR_DESCUENTO);
    }
}