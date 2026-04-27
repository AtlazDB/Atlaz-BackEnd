package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String registration;
    private String email;
    private String passwordHash;
    private Perfil profile;
    private String userStatus;
}
