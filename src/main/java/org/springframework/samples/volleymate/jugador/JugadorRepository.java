package org.springframework.samples.volleymate.jugador;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface JugadorRepository extends CrudRepository<Jugador, Integer> {
    
    @Query("SELECT j FROM Jugador j WHERE j.id=:id")
    public Jugador findById(@Param("id") int id);

    @Query("select j from Jugador j where j.user.username = ?1")
    public Jugador findByUsername(String username);

}
