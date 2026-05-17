package com.simulador.simuladorTactico.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String correo;
    private String password1; 
    private String password2; 
}