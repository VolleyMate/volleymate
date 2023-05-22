package org.springframework.samples.volleymate.aspecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AspectoService {

    @Autowired
	private AspectoRepository aspectoRepository;

    @Autowired
	public AspectoService(AspectoRepository aspectoRepository) {
		this.aspectoRepository = aspectoRepository;
	}

	@Transactional(rollbackFor = IllegalArgumentException.class)
	public void save(Aspecto aspecto) throws DataAccessException, IllegalArgumentException {
		aspectoRepository.save(aspecto);
	}
	
	@Transactional
	public List<Aspecto> findAllAspectos(){
		Iterable<Aspecto> it = aspectoRepository.findAll();
		List<Aspecto> result = new ArrayList<Aspecto>();
    	it.forEach(x-> result.add(x));
		return result;
	}

	@Transactional
	public void saveAspecto(@Valid Aspecto aspecto) {
		aspectoRepository.save(aspecto);
	}

	@Transactional
    public Aspecto findById(Integer aspectoId) {
        return this.aspectoRepository.findById(aspectoId).get();
    }
	
	@Transactional
    public List<Aspecto> findAllAspectosGratuitos() {
        return this.aspectoRepository.findAspectosGratuitos();
    }
    
	@Transactional
	public List<String> validarAspecto (Aspecto aspecto) {
		List<String> errores = new ArrayList<>();
		Integer digitos = (int)(Math.log10(aspecto.getPrecio())+1);

		if(aspecto.getImagen().isEmpty()){
			errores.add("La imagen no puede estar vacía");
		}
		if(digitos.toString().isEmpty()){
			errores.add("El precio no puede estar vacío");
		}
		return errores;
	}

	@Transactional
	public void deleteAspecto(Aspecto aspecto) {
		this.aspectoRepository.delete(aspecto);
	}

	public List<Aspecto> findAspectosDisponiblesAComprarPorJugador(Jugador jugador) {
		List<Aspecto> aspectoQueTiene = aspectoRepository.findAspectosDisponibles(jugador);
		List<Aspecto> aspectos = this.findAllAspectos();
	
		Iterator<Aspecto> iterator = aspectos.iterator();
		while (iterator.hasNext()) {
			Aspecto aspectoIt = iterator.next();
			for (Aspecto aspectoQueTieneIt : aspectoQueTiene) {
				if (aspectoQueTieneIt.equals(aspectoIt)) {
					iterator.remove();
					break; // Salimos del bucle interno si encontramos una coincidencia
				}
			}
		}
		return aspectos;
	}
	

}
