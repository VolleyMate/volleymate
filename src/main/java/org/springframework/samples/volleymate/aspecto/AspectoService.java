package org.springframework.samples.volleymate.aspecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AspectoService {

    @Autowired
	private AspectoRepository aspectoRepository;

    @Autowired
	public AspectoService(AspectoRepository aspectoRepository) {
		this.aspectoRepository = aspectoRepository;
	}
	
	@Transactional
	public List<Aspecto> findAllAspectos(){
		Iterable<Aspecto> it = aspectoRepository.findAll();
		List<Aspecto> result = new ArrayList<Aspecto>();
    	it.forEach(x-> result.add(x));
		return result;
	}

    public Aspecto findById(Integer aspectoId) {
        return this.aspectoRepository.findById(aspectoId).get();
    }

    public List<Aspecto> findAllAspectosGratuitos() {
        return this.aspectoRepository.findAspectosGratuitos();
    }
    
}
