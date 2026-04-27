package com.example.AtlazDB.dto;

import com.example.AtlazDB.enums.TipoCombustivelViatura;
import com.example.AtlazDB.enums.ViaturaStatus;
import com.example.AtlazDB.model.Viatura;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VehicleResponseDTO {

    private Long id;
    private String prefixo;
    private String modelo;
    private ViaturaStatus status;
    private TipoCombustivelViatura tipoCombustivel;

    public static VehicleResponseDTO fromEntity(Viatura viatura) {
        return new VehicleResponseDTO(
                viatura.getId(),
                viatura.getPrefixo(),
                viatura.getModelo().getNomeModelo() + " - " + viatura.getModelo().getNomeMarca(),
                viatura.getViaturaStatus(),
                viatura.getTipoCombustivel()
        );
    }
}