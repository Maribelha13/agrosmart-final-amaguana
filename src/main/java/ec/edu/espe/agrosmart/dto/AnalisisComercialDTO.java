package ec.edu.espe.agrosmart.dto;

public record AnalisisComercialDTO(
        ProductoDTO producto,
        String recomendacionIA,
        String viabilidadMercado
) {}