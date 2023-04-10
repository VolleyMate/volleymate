package org.springframework.samples.volleymate.centro;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CentroService {
	
	private CentroRepository centroRepository;

	private int tamanoPaginacionPorPagina = 6;

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
	public Page<Centro> findAcceptedCentrosPageable(int page) {
		Pageable pageable = PageRequest.of(page,tamanoPaginacionPorPagina);
		Page<Centro> centrosAceptados = centroRepository.findAcceptedCentrosPageable(pageable);
		return centrosAceptados;
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