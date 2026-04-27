package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TypeOccurrence;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderServiceRequestDTO {

    private TypeOccurrence typeService;
    private String localDestiny;
    private String justification;
    private String requisition;
    private BigDecimal leaveKm;
    private BigDecimal arriveKm;
    private LocalDateTime leaveDate;
    private LocalDateTime returnDate;
    private Long idUser;
    private Long idVehicle;


}
