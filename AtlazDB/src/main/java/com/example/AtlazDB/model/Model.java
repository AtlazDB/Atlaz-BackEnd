package com.example.AtlazDB.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "modelo")
@Data
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Long id;

    @Column(name = "nome_modelo")
    private String nameModel;

    @Column(name = "nome_marca")
    private String nameBrand;

}
