package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Jugador;
import com.simulador.simuladorTactico.persistence.enums.Posicion; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    List<Jugador> findByEntrenadorCorreo(String correo);

    List<Jugador> findByEntrenadorCorreoAndNombreContainingIgnoreCase(String correo, String nombre);
    List<Jugador> findByEntrenadorCorreoAndPosicion(String correo, Posicion posicion);
    
    boolean existsByEntrenadorCorreoAndDorsal(String correo, Integer dorsal);
    boolean existsByEntrenadorCorreoAndDorsalAndIdNot(String correo, Integer dorsal, Integer id);
}