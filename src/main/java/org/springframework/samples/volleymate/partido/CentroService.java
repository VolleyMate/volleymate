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
public class CentroService {
	
	private CentroRepository centroRepository;

	@Autowired
	public CentroService(CentroRepository centroRepository) {
		this.centroRepository = centroRepository;
	}
	
	@Transactional
	public List<Centro> findAllCentros(){
		return centroRepository.findAll();
	}

}