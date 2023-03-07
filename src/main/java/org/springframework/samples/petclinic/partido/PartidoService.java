package org.springframework.samples.petclinic.partido;


import java.util.List;
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
	@Transactional(readOnly = true)
    public Partido findPartidoById(int id) throws DataAccessException {
        return partidoRepository.findById(id).get();
    }

	@Transactional
	public void save(Partido partido) {
		partidoRepository.save(partido);
	}

	@Transactional
	public void deletePartido(@Valid Partido partido) throws DataAccessException, DataIntegrityViolationException {
		partidoRepository.delete(partido);
	}

	
}