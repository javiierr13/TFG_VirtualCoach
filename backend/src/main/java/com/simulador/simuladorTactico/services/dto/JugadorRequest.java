package com.simulador.simuladorTactico.services.dto;

import com.simulador.simuladorTactico.persistence.enums.PiernaDominante;
import com.simulador.simuladorTactico.persistence.enums.Posicion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JugadorRequest {
    private String nombre;
    private Integer dorsal;
    private Posicion posicion;      
    private PiernaDominante pierna; 
}