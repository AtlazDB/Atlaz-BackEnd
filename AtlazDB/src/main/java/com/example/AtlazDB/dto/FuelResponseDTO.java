package com.example.AtlazDB.dto;

import com.example.AtlazDB.model.Fuel;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FuelResponseDTO {

    private Long id;
    private LocalDateTime date;
    private BigDecimal value;
    private String numberReceipt;

    public static FuelResponseDTO fromEntity(Fuel fuel) {
        return new FuelResponseDTO(
                fuel.getId(),
                fuel.getDateHour(),
                fuel.getTotalValue(),
                fuel.getNumberReceipt()
        );
    }
}