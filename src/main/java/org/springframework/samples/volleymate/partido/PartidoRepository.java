package org.springframework.samples.volleymate.partido;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    List<Partido> findAll();

    @Query("SELECT p FROM Partido p WHERE p.id=:id")
    public Partido findById(@Param("id") int id);
}
