package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.application.AgrosmartApplication;
import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(ProductoController.class)
@ContextConfiguration(classes = {ProductoController.class, AgrosmartApplication.class})
class ProductoControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductoService productoService;

    @Test
    void testListarProductos() {
        Producto productoMock = new Producto(1L, "fertilizante organico", "Agricultura", new BigDecimal("25.50"), 100, List.of("admin@agrosmart.com"));

        when(productoService.listarProductos()).thenReturn(Flux.just(productoMock));

        webTestClient.get().uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nombre").isEqualTo("fertilizante organico");
    }

    @Test
    void testObtenerPorId() {
        Producto productoMock = new Producto(1L, "fertilizante organico", "Agricultura", new BigDecimal("25.50"), 100, List.of("admin@agrosmart.com"));

        when(productoService.obtenerPorId(1L)).thenReturn(Mono.just(productoMock));

        webTestClient.get().uri("/api/productos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("fertilizante organico");
    }
}