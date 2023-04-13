package org.springframework.samples.volleymate.aspecto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AspectoRepository extends CrudRepository<Aspecto, Integer> {
    
    List<Aspecto> findAll();
}
