package ec.edu.espe.agrosmart.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PublicidadService {

    public Mono<Void> notificarCampana(String nombreProducto) {
        return Mono.empty();
    }
}