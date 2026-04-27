package com.example.AtlazDB.model;

import jakarta.persistence.*;
import com.example.AtlazDB.enums.*;
import lombok.Data;

@Entity
@Table(name = "viatura")
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viatura")
    private Long id;

    private String prefix;

    @Enumerated(EnumType.STRING)
    private TipoViatura type;

    @Column(name = "viatura_status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Model model;
}
