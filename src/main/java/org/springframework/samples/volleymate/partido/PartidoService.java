package org.springframework.samples.volleymate.partido;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidoService {
	
	private PartidoRepository partidoRepository;

	private SolicitudRepository solicitudRepository;

	@Autowired
	public PartidoService(PartidoRepository partidoRepository, SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
		this.partidoRepository = partidoRepository;
	}
	
	@Transactional
	public List<Partido> findAllPartidos(){
		return partidoRepository.findAll();
	}

	@Transactional(rollbackFor = IllegalArgumentException.class)
	public void save(Partido partido) throws DataAccessException, IllegalArgumentException {
		if(partido.getFecha().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException();
		}else {
			partidoRepository.save(partido);
		}
	}

	@Transactional
	public void deletePartido(@Valid Partido partido) throws DataAccessException, DataIntegrityViolationException {
		partidoRepository.delete(partido);
	}

	public Partido findPartidoById(int id) throws DataAccessException{
		return partidoRepository.findById(id);
	}
	
  	public Set<Partido> getPartidosByCreatorId(Integer id) {
		return partidoRepository.getPartidosByCreatorId(id);
	}

	public Boolean getJugadorDentroPartido(Integer partidoId, Principal principal){
		Partido p = partidoRepository.findById(partidoId).get();
		Boolean estaDentro = false;
		List<Jugador> lista = p.getJugadores();
		for (Jugador jug:lista){
			if(jug.getUser().getUsername().equals(principal.getName())){
				estaDentro = true;
			}
		}
		return estaDentro;
	}


	public Boolean getJugadorEnEsperaPartido(Integer partidoId, Principal principal){
		Set<Solicitud> lista = solicitudRepository.findSolicitudesByPartidoId(partidoId);
		Boolean estaEnEspera = false;
		for (Solicitud solicitud:lista){
			if(solicitud.getJugador().getUser().getUsername().equals(principal.getName())){
				estaEnEspera = true;
			}
		}
		return estaEnEspera;
	}

	// Filtrar partidos

	public Set<Partido> getPartidosByTipo(Tipo tipo) {
		Set<Partido> conj = new HashSet<>();
		conj.addAll(partidoRepository.findPartidosByTipo(tipo));
		return conj;
	}

	public Set<Partido> getPartidosBySexo(Sexo sexo) {
		Set<Partido> conj = new HashSet<>();
		conj.addAll(partidoRepository.findPartidosBySexo(sexo));
		return conj;
	}

  public Set<Partido> getPartidosByLugar(String lugar) {
		Set<Partido> conj = new HashSet<>();
		conj.addAll(partidoRepository.findPartidosByLugar(lugar));
		return conj;
	}

}