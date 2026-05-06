package com.simulador.simuladorTactico.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulador.simuladorTactico.persistence.entities.Alineacion;
import com.simulador.simuladorTactico.persistence.entities.Entrenador;
import com.simulador.simuladorTactico.persistence.entities.Jugador;
import com.simulador.simuladorTactico.persistence.entities.Participa;
import com.simulador.simuladorTactico.persistence.repositories.AlineacionRepository;
import com.simulador.simuladorTactico.persistence.repositories.EntrenadorRepository;
import com.simulador.simuladorTactico.persistence.repositories.JugadorRepository;
import com.simulador.simuladorTactico.services.dto.AlineacionRequest;

@Service
public class AlineacionService {

    @Autowired
    private AlineacionRepository alineacionRepository;
    @Autowired
    private EntrenadorRepository entrenadorRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    @Transactional
    public Alineacion guardarAlineacion(AlineacionRequest request, String emailEntrenador) {
        Entrenador entrenador = entrenadorRepository.findByCorreo(emailEntrenador)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Alineacion alineacion = new Alineacion();
        alineacion.setNombre(request.getNombre());
        alineacion.setTipoFormacion(request.getTipoFormacion());
        alineacion.setTipoFutbol(request.getTipoFutbol());
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        for (AlineacionRequest.PosicionJugadorDTO posDTO : request.getPosiciones()) {
            Jugador jugador = jugadorRepository.findById(posDTO.getJugadorId())
                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado: " + posDTO.getJugadorId()));

            if (!jugador.getEntrenador().getCorreo().equals(emailEntrenador)) {
                throw new RuntimeException("El jugador con ID " + posDTO.getJugadorId() + " no pertenece a tu equipo.");
            }

            Participa participa = new Participa();
            participa.setAlineacion(alineacion);
            participa.setJugador(jugador);
            participa.setPosX(posDTO.getPosX());
            participa.setPosY(posDTO.getPosY());

            alineacion.getPosiciones().add(participa);
        }

        return alineacionRepository.save(alineacion);
    }
    
    public List<Alineacion> listarAlineaciones(String emailEntrenador) {
        return alineacionRepository.findByEntrenadorCorreo(emailEntrenador);
    }

    @Transactional
    public void borrarAlineacion(Integer idAlineacion, String emailEntrenador) {
        Alineacion alineacion = alineacionRepository.findById(idAlineacion)
                .orElseThrow(() -> new RuntimeException("Alineación no encontrada"));

        if (!alineacion.getEntrenador().getCorreo().equals(emailEntrenador)) {
            throw new RuntimeException("No tienes permiso para borrar esta alineación");
        }

        alineacionRepository.delete(alineacion);
    }

    public Alineacion obtenerAlineacion(Integer id, String emailEntrenador) {
        Alineacion alineacion = alineacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alineación no encontrada"));

        if (!alineacion.getEntrenador().getCorreo().equals(emailEntrenador)) {
            throw new RuntimeException("No tienes permiso para ver esta alineación");
        }

        return alineacion;
    }

    @Transactional
    public Alineacion actualizarAlineacion(Integer id, AlineacionRequest request, String emailEntrenador) {
        Alineacion alineacion = alineacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alineación no encontrada"));

        if (!alineacion.getEntrenador().getCorreo().equals(emailEntrenador)) {
            throw new RuntimeException("No tienes permiso para editar esta alineación");
        }

        alineacion.setNombre(request.getNombre());
        alineacion.setTipoFormacion(request.getTipoFormacion());
        alineacion.setTipoFutbol(request.getTipoFutbol());
        alineacion.getPosiciones().clear();

        for (AlineacionRequest.PosicionJugadorDTO posDTO : request.getPosiciones()) {
            Jugador jugador = jugadorRepository.findById(posDTO.getJugadorId())
                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado: " + posDTO.getJugadorId()));

            Participa participa = new Participa();
            participa.setAlineacion(alineacion);
            participa.setJugador(jugador);
            participa.setPosX(posDTO.getPosX());
            participa.setPosY(posDTO.getPosY());

            alineacion.getPosiciones().add(participa);
        }

        return alineacionRepository.save(alineacion);
    }
}