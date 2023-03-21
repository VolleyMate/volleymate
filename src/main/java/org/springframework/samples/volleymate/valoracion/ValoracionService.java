package org.springframework.samples.volleymate.valoracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValoracionService {

    private ValoracionRepository vRepository;

    @Autowired
	public ValoracionService(ValoracionRepository vRepository) {
		this.vRepository = vRepository;
	}

    @Transactional(rollbackFor = IllegalArgumentException.class)
	public void saveValoracion(Valoracion valoracion) throws DataAccessException, IllegalArgumentException {
		vRepository.save(valoracion);
	}

    public boolean valoracionExiste(Integer ratingPlayerId, Integer ratedPlayerId) {
        return vRepository.valoracionExiste(ratingPlayerId, ratedPlayerId) != 0;
    }
    
}
