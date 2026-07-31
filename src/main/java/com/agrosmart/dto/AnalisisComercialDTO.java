package com.agrosmart.dto;

public record AnalisisComercialDTO(
        ProductoDTO producto,
        String recomendacionIA,
        String viabilidadMercado
) {}