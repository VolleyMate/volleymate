package org.springframework.samples.petclinic.partido;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    List<Partido> findAll();
}
