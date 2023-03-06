package org.springframework.samples.petclinic.jugador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.partido.Partido;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JugadorService {
    
    private JugadorRepository jugadorRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    @Transactional(readOnly = true)
	public Jugador findJugadorByUsername(String username) throws DataAccessException {
		return jugadorRepository.findByUsername(username);
	}

}
