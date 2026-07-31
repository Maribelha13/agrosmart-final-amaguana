package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.dto.AnalisisComercialDTO;
import ec.edu.espe.agrosmart.service.ComercializacionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/comercializacion")
public class ComercializacionController {

    private final ComercializacionService comercializacionService;

    public ComercializacionController(ComercializacionService comercializacionService) {
        this.comercializacionService = comercializacionService;
    }

    @GetMapping("/analizar/{id}")
    public Mono<AnalisisComercialDTO> analizarViabilidad(@PathVariable Long id) {
        return comercializacionService.analizarViabilidad(id);
    }
}