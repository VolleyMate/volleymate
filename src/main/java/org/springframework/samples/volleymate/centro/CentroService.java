package org.springframework.samples.volleymate.centro;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional
	public void saveCentro(@Valid Centro centro) {
		centroRepository.save(centro);
	}

	@Transactional
	public List<Centro> findAcceptedCentros() {
		return centroRepository.findAcceptedCentros();
	}

	@Transactional
	public List<Centro> getSolicitudesCentros() {
		return centroRepository.getSolicitudesCentros();
	}

	@Transactional
	public Centro findCentroById(int centroId) {
		return centroRepository.getCentroById(centroId);
	}

	@Transactional
	public void deleteCentro(int centroId) {
		centroRepository.deleteCentroById(centroId);
	}



}