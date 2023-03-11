package org.springframework.samples.volleymate.jugador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoRepository;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JugadorService {
    
    private JugadorRepository jugadorRepository;
    private PartidoRepository partidoRepository;
    private SolicitudRepository solicitudRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository, PartidoRepository partidoRepository, SolicitudRepository solicitudRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
        this.solicitudRepository = solicitudRepository;
    }


    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorByUsername(String username) throws DataAccessException {
      return jugadorRepository.findByUsername(username);
    }


    public void unirsePartida(Jugador jugador, Partido partido) throws YaUnidoException{
        
        if(jugador.getPartidos().contains(partido)){
            throw new YaUnidoException();
        }
        else{
            //metemos dato en jugador; check para debuggear
            Set<Partido> partidos = new HashSet<>();
            if(jugador.getPartidos()!=null){
                partidos = jugador.getPartidos();
            }
            partidos.add(partido);
            jugador.setPartidos(partidos);

            //metemos dato en partido; if para debuggear
            List<Jugador> jugadores = new ArrayList<>();
            if(partido.getJugadores() != null){
                jugadores = partido.getJugadores();
            }
            jugadores.add(jugador);
            partido.setJugadores(jugadores);

            this.jugadorRepository.save(jugador);
            this.partidoRepository.save(partido);
        }
    }

    public void crearSolicitudPartido(Jugador jugador, Partido partido) {
        Solicitud solicitud = new Solicitud();
        solicitud.setJugador(jugador);
        solicitud.setPartido(partido);
        this.solicitudRepository.save(solicitud);
    }

    public Solicitud findSolicitudById(int solicitudId) {
        return this.solicitudRepository.findById(solicitudId).get();
    }

    public void eliminarSolicitud(Solicitud solicitud) {
        this.solicitudRepository.delete(solicitud);
    }
}
