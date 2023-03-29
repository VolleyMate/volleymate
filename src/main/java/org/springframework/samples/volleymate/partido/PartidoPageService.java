package org.springframework.samples.volleymate.partido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PartidoPageService {

	private PartidoPageRepository partidoPageRepo;

	@Autowired
	public PartidoPageService(PartidoPageRepository partidoPageRepo) {
		this.partidoPageRepo = partidoPageRepo;
	}
    
    @Transactional
    public Page<Partido> findAllPartidos(Pageable pageable) {
		return partidoPageRepo.findAll(pageable);
	}
	
}