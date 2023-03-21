package org.springframework.samples.volleymate.mensaje;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends CrudRepository<Mensaje, Integer> {
    
    @Query("SELECT s FROM Mensaje s WHERE s.partido.id = :partidoId")
    public Set<Mensaje> findMensajesByPartidoId(int partidoId);

    @Query("SELECT s FROM Mensaje s WHERE s.emisor = :jugador")
    public Set<Mensaje> findMensajesByJugador(Jugador jugador);
}


