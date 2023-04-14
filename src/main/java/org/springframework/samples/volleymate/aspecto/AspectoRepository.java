package org.springframework.samples.volleymate.aspecto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AspectoRepository extends CrudRepository<Aspecto, Integer> {
    
    List<Aspecto> findAll();

    Aspecto findById(int id);

    @Query("SELECT a FROM Aspecto a WHERE a.jugadorAdquirido.id = ?1")
    List<Aspecto> findAspectosByJugadorId(int id);
}
