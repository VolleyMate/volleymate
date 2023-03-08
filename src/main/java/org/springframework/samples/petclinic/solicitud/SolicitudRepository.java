package org.springframework.samples.petclinic.solicitud;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {
    
    @Query("SELECT s FROM Solicitud s WHEN s.partido.id = :partidoId")
    public Set<Solicitud> findSolicitudesByPartidoId(int partidoId);

}
