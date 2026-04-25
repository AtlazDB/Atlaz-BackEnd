package com.example.AtlazDB.dto;

import com.example.AtlazDB.model.Model;

public record ModelResponseDTO(
        Long id,
        String nomeModelo,
        String nomeMarca
) {
    // Construtor para converter a Entidade em DTO
    public ModelResponseDTO(Model model) {
        this(
                model.getId(),
                model.getNomeModelo(),
                model.getNomeMarca()
        );
    }
}