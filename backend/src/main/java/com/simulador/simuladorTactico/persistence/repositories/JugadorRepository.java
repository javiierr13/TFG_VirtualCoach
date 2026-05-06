package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Jugador;
import com.simulador.simuladorTactico.persistence.enums.Posicion; // Importante importar el Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    // Ya tenías este:
    List<Jugador> findByEntrenadorCorreo(String correo);

    // Buscar por nombre Y que pertenezca al entrenador logueado
    List<Jugador> findByEntrenadorCorreoAndNombreContainingIgnoreCase(String correo, String nombre);

    // Buscar por posición exacta Y que pertenezca al entrenador logueado
    List<Jugador> findByEntrenadorCorreoAndPosicion(String correo, Posicion posicion);
}