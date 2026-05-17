package com.simulador.simuladorTactico.persistence.repositories;

import com.simulador.simuladorTactico.persistence.entities.Participa;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface ParticipaRepository extends ListCrudRepository<Participa, Integer> {
    List<Participa> findByAlineacionId(Integer idAlineacion);
}