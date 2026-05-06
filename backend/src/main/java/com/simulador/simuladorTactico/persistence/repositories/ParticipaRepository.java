package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Participa;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface ParticipaRepository extends ListCrudRepository<Participa, Integer> {
    // Para recuperar todos los jugadores y sus posiciones de una alineación
    List<Participa> findByAlineacionId(Integer idAlineacion);
}