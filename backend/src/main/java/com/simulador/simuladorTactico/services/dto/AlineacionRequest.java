package com.simulador.simuladorTactico.services.dto;

import com.simulador.simuladorTactico.persistence.enums.TipoFutbol;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AlineacionRequest {
    private String nombre;
    private String tipoFormacion; 
    private TipoFutbol tipoFutbol; 
    private List<PosicionJugadorDTO> posiciones; 

    @Getter
    @Setter
    public static class PosicionJugadorDTO {
        private Integer jugadorId;
        private Integer posX;
        private Integer posY;
    }
}