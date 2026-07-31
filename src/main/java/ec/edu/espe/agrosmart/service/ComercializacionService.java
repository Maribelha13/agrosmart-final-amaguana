package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.ProductoMapper;
import ec.edu.espe.agrosmart.dto.AnalisisComercialDTO;
import ec.edu.espe.agrosmart.dto.ProductoDTO;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ComercializacionService {

    private final ProductoRepository productoRepository;
    private final AgroSmartAIService agroSmartAIService;

    public ComercializacionService(ProductoRepository productoRepository, AgroSmartAIService agroSmartAIService) {
        this.productoRepository = productoRepository;
        this.agroSmartAIService = agroSmartAIService;
    }

    public Flux<ProductoDTO> listarProductosPorCategoria(String categoria) {
        return Flux.defer(() -> Flux.fromIterable(productoRepository.findByCategoria(categoria)))
                .map(ProductoDTO::fromEntity);
    }

    public Mono<ProductoDTO> guardarProducto(ProductoDTO dto) {
        return Mono.just(dto);
    }

    public Mono<AnalisisComercialDTO> analizarViabilidad(Long id) {
        return agroSmartAIService.analizarViabilidad(id);
    }
}