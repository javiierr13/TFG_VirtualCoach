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
import org.springframework.web.bind.annotation.RestController;

import com.simulador.simuladorTactico.persistence.entities.Alineacion;
import com.simulador.simuladorTactico.services.AlineacionService;
import com.simulador.simuladorTactico.services.dto.AlineacionRequest;

@RestController
@RequestMapping("/api/alineaciones")
public class AlineacionController {

	@Autowired
	private AlineacionService alineacionService;

	@PostMapping
	public ResponseEntity<Alineacion> guardar(@RequestBody AlineacionRequest request, Principal principal) {
		return ResponseEntity.ok(alineacionService.guardarAlineacion(request, principal.getName()));
	}

	@GetMapping
	public ResponseEntity<List<Alineacion>> listar(Principal principal) {
		return ResponseEntity.ok(alineacionService.listarAlineaciones(principal.getName()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrar(@PathVariable Integer id, Principal principal) {
		alineacionService.borrarAlineacion(id, principal.getName());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Alineacion> obtener(@PathVariable Integer id, Principal principal) {
		return ResponseEntity.ok(alineacionService.obtenerAlineacion(id, principal.getName()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Alineacion> actualizar(@PathVariable Integer id, @RequestBody AlineacionRequest request, Principal principal) {
		return ResponseEntity.ok(alineacionService.actualizarAlineacion(id, request, principal.getName()));
	}
}