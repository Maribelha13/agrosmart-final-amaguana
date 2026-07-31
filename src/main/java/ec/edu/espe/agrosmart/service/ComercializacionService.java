package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.dto.AnalisisComercialDTO;
import ec.edu.espe.agrosmart.dto.ProductoDTO;
import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ComercializacionService {

    private final ProductoRepository productoRepository;

    /**
     * Obtiene los productos de la categoría asignada (Banano) o de la base general,
     * adaptando la llamada bloqueante de JPA a la pila reactiva con boundedElastic.
     */
    public Flux<ProductoDTO> listarProductosPorCategoria(String categoria) {
        return Mono.fromCallable(() -> productoRepository.findByCategoriaIgnoreCase(categoria))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(ProductoDTO::fromEntity);
    }

    /**
     * Guarda un producto en la base de datos PostgreSQL en un hilo elástico.
     */
    public Mono<ProductoDTO> guardarProducto(ProductoDTO dto) {
        return Mono.fromCallable(() -> {
                    ProductoEntity entity = ProductoEntity.builder()
                            .nombre(dto.nombre())
                            .categoria(dto.categoria() != null ? dto.categoria() : "Banano")
                            .precio(dto.precio())
                            .stock(dto.stock())
                            .descripcion(dto.descripcion())
                            .build();
                    return productoRepository.save(entity);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(ProductoDTO::fromEntity);
    }

    /**
     * Genera un análisis de viabilidad comercial reactivo para un producto.
     */
    public Mono<AnalisisComercialDTO> analizarViabilidad(Long productoId) {
        return Mono.fromCallable(() -> productoRepository.findById(productoId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalEntity -> optionalEntity
                        .map(entity -> {
                            ProductoDTO dto = ProductoDTO.fromEntity(entity);
                            String viabilidad = entity.getPrecio().compareTo(new BigDecimal("10.00")) < 0
                                    ? "Alta demanda esperada por precio competitivo"
                                    : "Mercado premium con margen ajustado";
                            String recomendacion = "Optimizar canal de distribución para la categoría " + entity.getCategoria();
                            return Mono.just(new AnalisisComercialDTO(dto, recomendacion, viabilidad));
                        })
                        .orElseGet(() -> Mono.empty())
                );
    }
}