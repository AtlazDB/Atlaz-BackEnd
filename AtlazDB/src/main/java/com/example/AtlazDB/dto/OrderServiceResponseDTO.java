package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TypeOccurrence;
import com.example.AtlazDB.model.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderServiceResponseDTO(
        Long id,
        TypeOccurrence tipoServico,
        String localDestino,
        String justificativa,
        String requisitante,
        BigDecimal kmSaida,
        BigDecimal kmChegada,
        LocalDateTime dataSaida,
        LocalDateTime dataRetorno
) {
    // Construtor para converter a Entidade em DTO
    public OrderServiceResponseDTO(OrderService orderService) {
        this(
                orderService.getId(),
                orderService.getTipoServico(),
                orderService.getLocalDestino(),
                orderService.getJustificativa(),
                orderService.getRequisitante(),
                orderService.getKmSaida(),
                orderService.getKmChegada(),
                orderService.getDataSaida(),
                orderService.getDataRetorno()
        );
    }
}