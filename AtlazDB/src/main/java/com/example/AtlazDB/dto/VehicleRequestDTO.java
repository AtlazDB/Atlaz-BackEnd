package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TipoViatura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequestDTO {

    private String prefix;
    private TipoViatura type;
    private Long idModel;
}
