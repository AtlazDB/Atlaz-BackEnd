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
    private LocalDateTime data;
    private BigDecimal valor;
    private String numeroNotaFiscal;

    public static FuelResponseDTO fromEntity(Fuel abast) {
        return new FuelResponseDTO(
                abast.getId(),
                abast.getDateHour(),
                abast.getValorTotal(),
                abast.getNumeroNotaFiscal()
        );
    }
}