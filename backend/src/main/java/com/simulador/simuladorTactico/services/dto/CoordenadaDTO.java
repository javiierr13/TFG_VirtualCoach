package com.simulador.simuladorTactico.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordenadaDTO {
    private String etiqueta; 
    private double x;        
    private double y;        
}