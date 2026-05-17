package com.simulador.simuladorTactico.web.controllers;

import com.simulador.simuladorTactico.services.FormacionRivalService;
import com.simulador.simuladorTactico.services.dto.CoordenadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rivales")
public class FormacionRivalController {

    @Autowired
    private FormacionRivalService service;

    
    @GetMapping("/formacion")
    public ResponseEntity<List<CoordenadaDTO>> obtenerRival(
            @RequestParam String tipo,
            @RequestParam String nombre) {
        return ResponseEntity.ok(service.obtenerFormacion(tipo, nombre));
    }
    
    
    @GetMapping("/lista")
    public ResponseEntity<List<String>> listarOpciones() {
        return ResponseEntity.ok(service.listarDisponibles());
    }
}