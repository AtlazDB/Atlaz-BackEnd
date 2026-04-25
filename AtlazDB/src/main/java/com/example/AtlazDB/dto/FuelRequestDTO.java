package com.example.AtlazDB.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FuelRequestDTO {
    private LocalDateTime dataHora;
    private BigDecimal litros;
    private BigDecimal valorTotal;
    private String numeroNotaFiscal;
    private Long idUsuario;
    private Long idViatura;
    private Long idCidade;
    private Long idOs;

}
