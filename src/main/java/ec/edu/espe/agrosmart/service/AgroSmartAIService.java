package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.dto.AnalisisComercialDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AgroSmartAIService {

    public Mono<AnalisisComercialDTO> analizarViabilidad(Long idProducto) {
        // Tu lógica con LangChain4j aquí, asegurándote de retornar AnalisisComercialDTO
        return Mono.empty();
    }
}