package com.example.AtlazDB.model;

import com.example.AtlazDB.enums.Perfil;
import com.example.AtlazDB.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    private String name;

    private String registration;

    private String email;

    @Column(name = "senha_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Perfil profile;

    @Column(name = "usuario_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

 }
