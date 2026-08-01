package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.domain.Producto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    // Simulación de un repositorio reactivo en memoria para el ejemplo
    private final List<Producto> productosBase = List.of(
            new Producto(1L, "fertilizante organico", "Agricultura", new BigDecimal("25.50"), 100, List.of("admin@agrosmart.com")),
            new Producto(2L, "Semilla de Maíz", "Agricultura", new BigDecimal("15.00"), 200, List.of("admin@agrosmart.com"))
    );

    // Obtener todos los productos como un Flux
    @GetMapping
    public Flux<Producto> listarProductos() {
        return Flux.fromIterable(productosBase);
    }

    // Obtener un producto por ID como un Mono
    @GetMapping("/{id}")
    public Mono<Producto> obtenerPorId(@PathVariable Long id) {
        Producto productoEncontrado = productosBase.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (productoEncontrado != null) {
            return Mono.just(productoEncontrado);
        } else {
            return Mono.empty();
        }
    }
}