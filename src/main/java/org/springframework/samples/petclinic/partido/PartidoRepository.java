package org.springframework.samples.petclinic.partido;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    List<Partido> findAll();
    
    Partido findById(int id) throws DataAccessException;
}
