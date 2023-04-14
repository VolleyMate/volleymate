package org.springframework.samples.volleymate.partido;


import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PartidoService {
	
	private PartidoRepository partidoRepository;

	private SolicitudRepository solicitudRepository;

	@Autowired
	private PartidoPageRepository partidoPageRepository;

	@Autowired
	public PartidoService(PartidoRepository partidoRepository, SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
		this.partidoRepository = partidoRepository;
	}

	private int tamanoPaginacionPorPagina = 6;
	
	@Transactional
	public List<Partido> findAllPartidos(){
		return partidoRepository.findAll();
	}

	@Transactional(rollbackFor = IllegalArgumentException.class)
	public void save(Partido partido) throws DataAccessException, IllegalArgumentException {
		partidoRepository.save(partido);
	}

	@Transactional
	public void deletePartido(@Valid Partido partido) throws DataAccessException, DataIntegrityViolationException {

		for (Jugador jugador : partido.getJugadores()) {
			if(!partido.getCreador().equals(jugador)){
				jugador.setVolleys(jugador.getVolleys() + 150);
			}
			jugador.getPartidos().remove(partido);
		}
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
	public Page<Partido> filtrarPartidos(Sexo sexo, Tipo tipoPartido, int page) {
		
		Pageable pageable = PageRequest.of(page,tamanoPaginacionPorPagina);
		Page<Partido> partidosSinFiltrar = partidoPageRepository.findAll(pageable);
		
        if (sexo != null && tipoPartido != null) {
            Page<Partido> partidosPorSexoYTipo = partidoPageRepository.findBySexoAndTipo(pageable, sexo, tipoPartido);
			return partidosPorSexoYTipo;
        } else if (sexo == null && tipoPartido != null) {
			Page<Partido> partidosPorTipo = partidoPageRepository.findByTipo(pageable, tipoPartido);
			return partidosPorTipo;
		} else if (sexo != null && tipoPartido == null) {
			Page<Partido> partidosPorSexo = partidoPageRepository.findBySexo(pageable, sexo);
			return partidosPorSexo;
        } else {
			return partidosSinFiltrar;
		}
        
    }

	public Set<String> getCiudades() {
		List<Partido> partidos = partidoRepository.findAll();
		Set<String> ciudades = partidos.stream().map(p -> p.getCentro().getCiudad())
			.map(c -> c.replace("á", "a").replace("é", "e")
			.replace("í", "i").replace("ó", "o")
			.replace("ú", "u").toUpperCase()).collect(Collectors.toSet());
		return ciudades;
	}

  public List<Partido> getPartidosDelJugador(Integer page, Pageable pageable, Jugador jugador){
    return partidoRepository.findAllPageable(pageable).stream()
    						.filter(p->p.getJugadores().contains(jugador))
    						.collect(Collectors.toList());
  }

  	public Page<Partido> buscarPartidosPorJugador (int page, Jugador jugador){
		Pageable pageable = PageRequest.of(page,tamanoPaginacionPorPagina);
		return partidoPageRepository.findByJugadoresId(pageable, jugador.getId());
	}

	public void salirPartido (Partido partido, Jugador jugador){
		Set<Partido> partidos = jugador.getPartidos();
		partidos.remove(partido);
		jugador.setPartidos(partidos);
		
		List<Jugador> jugadores = partido.getJugadores();
		jugadores.remove(jugador);
		partido.setJugadores(jugadores);

		partidoRepository.save(partido);
	}

	public boolean puedeEditarPartido(Jugador jugador, Partido partido) {
		return partido.getCreador().getId() == jugador.getId() && partido.getJugadores().size() == 1;
	}

}
