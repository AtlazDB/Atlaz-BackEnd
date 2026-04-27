package com.example.AtlazDB.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FuelRequestDTO {
    private LocalDateTime dateHour;
    private BigDecimal liters;
    private BigDecimal totalValue;
    private String numberReceipt;
    private Long idUser;
    private Long idVehicle;
    private Long idCity;
    private Long idOs;

}
