package org.springframework.samples.volleymate.solicitud;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.volleymate.jugador.Jugador;

public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {
    
    @Query("SELECT s FROM Solicitud s WHERE s.partido.id = :partidoId")
    public Set<Solicitud> findSolicitudesByPartidoId(int partidoId);

    @Query("SELECT s FROM Solicitud s WHERE s.partido.creador = :jugador")
    public Set<Solicitud> findSolicitudesATusPartidos(Jugador jugador);

    @Query("SELECT s FROM Solicitud s WHERE s.jugador = :jugador")
    public Set<Solicitud> findSolicitudesByJugador(Jugador jugador);
}


