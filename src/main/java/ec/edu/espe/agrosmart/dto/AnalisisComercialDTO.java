package ec.edu.espe.agrosmart.dto;

import java.math.BigDecimal;

public record AnalisisComercialDTO(
        Long idProducto,
        String recomendacion,
        BigDecimal puntajeViabilidad
) {}