package org.springframework.samples.petclinic.jugador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.partido.Partido;
import org.springframework.samples.petclinic.partido.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JugadorService {
    
    private JugadorRepository jugadorRepository;
    private PartidoRepository partidoRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    public void unirsePartida(int jugadorId, int partidoId) throws YaUnidoException{
        Jugador jugador = this.jugadorRepository.findById(jugadorId);
        Partido partido = this.partidoRepository.findById(partidoId);
        
        if(jugador.getPartidos().contains(partido)){
            throw new YaUnidoException();
        }
        else{
            //metemos dato en jugador
            Set<Partido> partidos = jugador.getPartidos();
            partidos.add(partido);
            jugador.setPartidos(partidos);
            //metemos dato en partido
            List<Jugador> jugadores = partido.getJugadores();
            jugadores.add(jugador);
            partido.setJugadores(jugadores);

            this.jugadorRepository.save(jugador);
            this.partidoRepository.save(partido);

        }
    }


}
