package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TypeOccurrence;
import com.example.AtlazDB.model.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderServiceResponseDTO(
        Long id,
        TypeOccurrence typoService,
        String localDestiny,
        String justification,
        String requisition,
        BigDecimal leaveKm,
        BigDecimal arriveKm,
        LocalDateTime leaveDate,
        LocalDateTime returnDate
) {

    public OrderServiceResponseDTO(OrderService orderService) {
        this(
                orderService.getId(),
                orderService.getTypeService(),
                orderService.getLocalDestiny(),
                orderService.getJustification(),
                orderService.getRequisition(),
                orderService.getLeaveKm(),
                orderService.getArriveKm(),
                orderService.getLeaveDate(),
                orderService.getReturnDate()
        );
    }
}