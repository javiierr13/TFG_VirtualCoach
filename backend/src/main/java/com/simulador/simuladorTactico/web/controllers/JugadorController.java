package com.simulador.simuladorTactico.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simulador.simuladorTactico.persistence.entities.Jugador;
import com.simulador.simuladorTactico.persistence.enums.Posicion;
import com.simulador.simuladorTactico.services.JugadorService;
import com.simulador.simuladorTactico.services.dto.JugadorRequest;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody JugadorRequest request, Principal principal) {
        
        return ResponseEntity.ok(jugadorService.crearJugador(request, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> listarJugadores(Principal principal) {
        return ResponseEntity.ok(jugadorService.listarMisJugadores(principal.getName()));
    }
    
 
    @GetMapping("/buscar-nombre")
    public ResponseEntity<List<Jugador>> buscarPorNombre(@RequestParam String nombre, Principal principal) {
        return ResponseEntity.ok(jugadorService.buscarPorNombre(nombre, principal.getName()));
    }

    
    @GetMapping("/buscar-posicion")
    public ResponseEntity<List<Jugador>> buscarPorPosicion(@RequestParam Posicion posicion, Principal principal) {
        return ResponseEntity.ok(jugadorService.buscarPorPosicion(posicion, principal.getName()));
    }
    
    
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Integer id, Principal principal) {
        jugadorService.eliminarJugador(id, principal.getName());
        
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(@PathVariable Integer id, @RequestBody JugadorRequest request, Principal principal) {
        return ResponseEntity.ok(jugadorService.actualizarJugador(id, request, principal.getName()));
    }
}