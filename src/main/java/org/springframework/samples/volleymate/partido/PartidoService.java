package org.springframework.samples.volleymate.partido;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidoService {
	
	private PartidoRepository partidoRepository;

	@Autowired
	public PartidoService(PartidoRepository partidoRepository) {
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

}