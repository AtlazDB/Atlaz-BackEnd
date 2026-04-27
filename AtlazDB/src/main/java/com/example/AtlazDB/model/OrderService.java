package com.example.AtlazDB.model;

import com.example.AtlazDB.enums.TypeOccurrence;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_servico")
@Data
public class OrderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_os")
    private Long id;

//    @Column(name = "numero_os")
//    private String numeroOs;

    @Column(name = "tipo_servico")
    @Enumerated(EnumType.STRING)
    private TypeOccurrence typeService;

    @Column(name = "local_destino")
    private String localDestiny;

    @Column(name = "justification")
    private String justification;

    @Column(name = "requisition")
    private String requisition;

    @Column(name = "km_saida")
    private BigDecimal leaveKm;

    @Column(name = "km_chegada")
    private BigDecimal arriveKm;

    @Column(name = "data_saida")
    private LocalDateTime leaveDate;

    @Column(name = "data_retorno")
    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_viatura")
    private Vehicle vehicle;

}
