package org.springframework.samples.volleymate.aspecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
		return aspectoRepository.findAll();
	}
    
}
