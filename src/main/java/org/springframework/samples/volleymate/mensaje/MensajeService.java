package org.springframework.samples.volleymate.mensaje;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            mensajeRepository.save(mensaje);  
	    }

        @Transactional
        public List<String> getParseoMensaje(String contenidoMensaje) {
            String[] palabrasSeparadas = contenidoMensaje.split(" ");
            List<String> palabrasLista = Arrays.asList(palabrasSeparadas);
            List<String> palabrasEnMinuscula = palabrasLista.stream().map(String::toLowerCase).collect(Collectors.toList());
            return palabrasEnMinuscula;
        }

        @Transactional
        public List<String> getListaDeInsultos () {
            // ⚠️ Hay que poner palabras en minúsculas en el array, el método getParseoMensaje hace la conversión
            return Arrays.asList("puta", "cabron", "cabrón", "cabrona", 
                "cabronazo", "cabronazo", "cabronazos", "cabrona", "cabronas","puto", "putos", "putas");
        }

        @Transactional
        public void deleteMensajesByJugador(Jugador jugador) {
            Set<Mensaje> mensajes = findTusMensajes(jugador);
            mensajeRepository.deleteAll(mensajes);
        }
    
}