package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.dto.AnalisisComercialDTO;
import ec.edu.espe.agrosmart.dto.ProductoDTO;
import ec.edu.espe.agrosmart.service.ComercializacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/comercializacion")
@RequiredArgsConstructor
public class ComercializacionController {

    private final ComercializacionService comercializacionService;

    @GetMapping("/productos")
    public Flux<ProductoDTO> obtenerProductos(@RequestParam(defaultValue = "Banano") String categoria) {
        return comercializacionService.listarProductosPorCategoria(categoria);
    }

    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        return comercializacionService.guardarProducto(dto);
    }

    @GetMapping("/analisis/{id}")
    public Mono<AnalisisComercialDTO> analizarProducto(@PathVariable Long id) {
        return comercializacionService.analizarViabilidad(id);
    }
}