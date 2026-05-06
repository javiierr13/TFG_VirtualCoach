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

    // GET http://localhost:8080/api/rivales/formacion?tipo=FUTBOL_11&nombre=4-4-2
    @GetMapping("/formacion")
    public ResponseEntity<List<CoordenadaDTO>> obtenerRival(
            @RequestParam String tipo,
            @RequestParam String nombre) {
        return ResponseEntity.ok(service.obtenerFormacion(tipo, nombre));
    }
    
    // GET http://localhost:8080/api/rivales/lista
    @GetMapping("/lista")
    public ResponseEntity<List<String>> listarOpciones() {
        return ResponseEntity.ok(service.listarDisponibles());
    }
}