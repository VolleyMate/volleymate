package org.springframework.samples.petclinic.partido;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PartidoRepository extends CrudRepository<Partido, Integer> {
    List<Partido> findAll();

    @Query("SELECT P FROM Partido P WHERE P.creador.id =:id")
	Set<Partido> getPartidosByCreatorId(@Param("id") int id);
}
