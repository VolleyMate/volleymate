package org.springframework.samples.volleymate.solicitud;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

    private SolicitudRepository solicitudRepository;

    @Autowired
    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional
    public Set<Solicitud> findAllSolicitudesByPartidoId(int id) throws DataAccessException {
        return solicitudRepository.findSolicitudesByPartidoId(id);
    }

    public Set<Solicitud> findSolicitudesATusPartidos(Jugador jugador) {
        return solicitudRepository.findSolicitudesATusPartidos(jugador);
    }

    public Set<Solicitud> findTusSolicitudes(Jugador jugador) {
        return solicitudRepository.findSolicitudesByJugador(jugador);
    }
    

}
