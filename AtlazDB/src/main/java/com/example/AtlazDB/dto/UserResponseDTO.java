package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.Perfil;
import com.example.AtlazDB.model.User;


import com.example.AtlazDB.enums.UserStatus;

public record UserResponseDTO(
        Long id,
        String name,
        String registration,
        String email,
        Perfil profile,
        UserStatus userStatus
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getRegistration(),
                user.getEmail(),
                user.getProfile(),
                user.getUserStatus()
        );
    }
}