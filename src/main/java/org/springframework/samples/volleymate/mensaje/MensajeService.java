package org.springframework.samples.volleymate.mensaje;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {
        
    private MensajeRepository mensajeRepository;
    
        @Autowired
        public void MensajeRepository(MensajeRepository mensajeRepository) {
            this.mensajeRepository = mensajeRepository;
        }
    
        @Transactional
        public Set<Mensaje> findAllMensajesByPartidoId(int id) throws DataAccessException {
            return mensajeRepository.findMensajesByPartidoId(id);
        }
    
        public Set<Mensaje> findTusMensajes(Jugador jugador) {
            return mensajeRepository.findMensajesByJugador(jugador);
        }

        @Transactional()
	    public void save(Mensaje mensaje) throws DataAccessException, IllegalArgumentException {
            //se podría poner filtro de spam o palabras como insultos
            // if(partido.getFecha().isBefore(LocalDateTime.now())) {
            //     throw new IllegalArgumentException();
            // }else {
                mensajeRepository.save(mensaje);
            // }   
	    }
    
}
