package org.springframework.samples.volleymate.centro;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;

@Service
public class CentroService {
	@Autowired
	private CentroRepository centroRepository;
	@Autowired
	private PartidoService partidoService;

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
	public void deleteCentro(Centro centro) {
		centroRepository.delete(centro);
	}

	public List<String> validarCentro (Centro centro) {
		List<String> errores = new ArrayList<>();
		String regex = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(centro.getMaps());

		if(centro.getNombre().isEmpty()){
			errores.add("El nombre no puede estar vacío");
		}
		if(centro.getDireccion().isEmpty()){
			errores.add("La dirección no puede estar vacía");
		}
		if(centro.getCiudad().isEmpty()){
			errores.add("La ciudad no puede estar vacía");
		}
		if(centro.getMaps().isEmpty()){
			errores.add("La dirección en maps no puede estar vacía");
		}
		if(!matcher.matches()){
			errores.add("La url no es válida");
		}
		if(!centro.getMaps().contains("maps")){
			errores.add("La dirección en maps no es válida, usa un link de google maps");
		}
		return errores;
	}



}