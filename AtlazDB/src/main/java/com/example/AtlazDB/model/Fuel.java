package com.example.AtlazDB.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.AtlazDB.enums.*;
import lombok.Data;

@Entity
@Table(name = "abastecimento")
@Data
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abastecimento")
    private Long id;

    @Column(name = "data_hora")
    private LocalDateTime dateHour;

    @Column(name = "km_atual")
    private BigDecimal currentKm;

    private BigDecimal litros;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "tipo_combustivel")
    @Enumerated(EnumType.STRING)
    private TypeFuel typeFuel;

    @Column(name = "numero_nota_fiscal")
    private String numeroNotaFiscal;

    @Column(name = "observacao_estado")
    private String observacaoEstado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_viatura")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_os")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private OrderService orderService;
}