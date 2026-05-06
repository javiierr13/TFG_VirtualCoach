package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Alineacion;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface AlineacionRepository extends ListCrudRepository<Alineacion, Integer> {
    List<Alineacion> findByEntrenadorCorreo(String correo);
}