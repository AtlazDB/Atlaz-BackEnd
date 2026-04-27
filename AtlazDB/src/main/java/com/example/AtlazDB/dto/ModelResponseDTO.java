package com.example.AtlazDB.dto;

import com.example.AtlazDB.model.Model;

public record ModelResponseDTO(
        Long id,
        String nameModel,
        String nameBrand
) {
    // Construtor para converter a Entidade em DTO
    public ModelResponseDTO(Model model) {
        this(
                model.getId(),
                model.getNameModel(),
                model.getNameBrand()
        );
    }
}