package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TipoViatura;
import com.example.AtlazDB.model.Vehicle;

public record VehicleResponseDTO(
        Long id,
        String prefixo,
        TipoViatura tipo,
        Long idModelo
) {
    // Construtor para converter a Entidade em DTO
    public VehicleResponseDTO(Vehicle vehicle) {
        this(
                vehicle.getId(),
                vehicle.getPrefixo(),
                vehicle.getTipo(),
                vehicle.getModel().getId()
        );
    }
}