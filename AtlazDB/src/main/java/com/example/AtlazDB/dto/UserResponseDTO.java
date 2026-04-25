package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.Perfil;
import com.example.AtlazDB.model.User;


import com.example.AtlazDB.enums.UsuarioStatus; 

public record UserResponseDTO(
        Long id,
        String nome,
        String matricula,
        String email,
        Perfil perfil,
        UsuarioStatus usuarioStatus 
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getNome(),
                user.getMatricula(),
                user.getEmail(),
                user.getPerfil(),
                user.getUsuarioStatus()
        );
    }
}