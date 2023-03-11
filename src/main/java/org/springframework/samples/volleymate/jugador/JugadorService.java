package org.springframework.samples.volleymate.jugador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoRepository;
import org.springframework.samples.volleymate.user.AuthoritiesRepository;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.UserRepository;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JugadorService {
    
    private JugadorRepository jugadorRepository;
    private PartidoRepository partidoRepository;
    private UserService userService;
    private AuthoritiesService authoritiesService;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository, PartidoRepository partidoRepository, UserService userService,AuthoritiesService authoritiesService ) {
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
        this.userService=userService;
        this.authoritiesService=authoritiesService;
    }


    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorByUsername(String username) throws DataAccessException {
      return jugadorRepository.findByUsername(username);
    }


    @Transactional
	public void saveJugador(@Valid Jugador jugador) throws DataAccessException, DataIntegrityViolationException {
		jugadorRepository.save(jugador);
		userService.saveUser(jugador.getUser());
		authoritiesService.saveAuthorities(jugador.getUser().getUsername(), "jugador");
	}


    public void unirsePartida(int jugadorId, int partidoId) throws YaUnidoException{
        Jugador jugador = this.jugadorRepository.findById(jugadorId);
        Partido partido = this.partidoRepository.findById(partidoId);
        
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
}
