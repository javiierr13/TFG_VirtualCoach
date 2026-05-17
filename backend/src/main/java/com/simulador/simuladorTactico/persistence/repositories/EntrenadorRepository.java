package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Entrenador;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface EntrenadorRepository extends ListCrudRepository<Entrenador, Integer> {
    
    Optional<Entrenador> findByCorreo(String correo);
}