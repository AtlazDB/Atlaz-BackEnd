package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TipoViatura;
import com.example.AtlazDB.model.Vehicle;

public record VehicleResponseDTO(
        Long id,
        String prefix,
        TipoViatura type,
        Long idModel
) {
    // Construtor para converter a Entidade em DTO
    public VehicleResponseDTO(Vehicle vehicle) {
        this(
                vehicle.getId(),
                vehicle.getPrefix(),
                vehicle.getType(),
                vehicle.getModel().getId()
        );
    }
}