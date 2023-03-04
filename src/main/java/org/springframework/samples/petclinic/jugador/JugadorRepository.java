package org.springframework.samples.petclinic.jugador;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;

public interface JugadorRepository extends Repository<Jugador, Integer> {
    
    @Query("SELECT j FROM Jugador j WHERE j.id=:id")
    public Jugador findById(@Param("id") int id);

}
