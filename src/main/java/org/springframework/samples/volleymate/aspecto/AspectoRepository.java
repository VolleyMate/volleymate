package org.springframework.samples.volleymate.aspecto;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.stereotype.Repository;

@Repository
public interface AspectoRepository extends CrudRepository<Aspecto, Integer> {
    
    @Query("SELECT a FROM Aspecto a WHERE a.precio = 0")
    public List<Aspecto> findAspectosGratuitos();

    @Query("SELECT a FROM Aspecto a JOIN a.jugadores j WHERE j = :jugador")
    public List<Aspecto> findAspectosDisponibles(Jugador jugador);


}
