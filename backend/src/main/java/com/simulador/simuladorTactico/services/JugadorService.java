package com.simulador.simuladorTactico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulador.simuladorTactico.persistence.entities.Entrenador;
import com.simulador.simuladorTactico.persistence.entities.Jugador;
import com.simulador.simuladorTactico.persistence.enums.Posicion;
import com.simulador.simuladorTactico.persistence.repositories.EntrenadorRepository;
import com.simulador.simuladorTactico.persistence.repositories.JugadorRepository;
import com.simulador.simuladorTactico.services.dto.JugadorRequest;

@Service
public class JugadorService {

	@Autowired
	private JugadorRepository jugadorRepository;

	@Autowired
	private EntrenadorRepository entrenadorRepository;

	
	public Jugador crearJugador(JugadorRequest request, String emailEntrenador) {
		Entrenador entrenador = entrenadorRepository.findByCorreo(emailEntrenador)
				.orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

		Jugador jugador = new Jugador();
		jugador.setNombre(request.getNombre());
		jugador.setDorsal(request.getDorsal());
		jugador.setPosicion(request.getPosicion());
		jugador.setPiernaDominante(request.getPierna());
		jugador.setEntrenador(entrenador); 

		return jugadorRepository.save(jugador);
	}

	
	public List<Jugador> listarMisJugadores(String emailEntrenador) {
		return jugadorRepository.findByEntrenadorCorreo(emailEntrenador);
	}

	
	public List<Jugador> buscarPorNombre(String nombre, String emailEntrenador) {
		return jugadorRepository.findByEntrenadorCorreoAndNombreContainingIgnoreCase(emailEntrenador, nombre);
	}

	
	public List<Jugador> buscarPorPosicion(Posicion posicion, String emailEntrenador) {
		return jugadorRepository.findByEntrenadorCorreoAndPosicion(emailEntrenador, posicion);
	}

	public void eliminarJugador(Integer idJugador, String emailEntrenador) {
		
		Jugador jugador = jugadorRepository.findById(idJugador)
				.orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

		
		
		if (!jugador.getEntrenador().getCorreo().equals(emailEntrenador)) {
			throw new RuntimeException("No tienes permiso para eliminar este jugador (no es tuyo)");
		}

		
		jugadorRepository.delete(jugador);
	}

	public Jugador actualizarJugador(Integer id, JugadorRequest request, String emailEntrenador) {
		Jugador jugador = jugadorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

		
		if (!jugador.getEntrenador().getCorreo().equals(emailEntrenador)) {
			throw new RuntimeException("No tienes permiso para editar este jugador");
		}

		jugador.setNombre(request.getNombre());
		jugador.setDorsal(request.getDorsal());
		jugador.setPosicion(request.getPosicion());
		jugador.setPiernaDominante(request.getPierna());

		return jugadorRepository.save(jugador);
	}
}