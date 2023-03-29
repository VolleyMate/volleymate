package org.springframework.samples.volleymate.centro;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CentroRepository extends CrudRepository<Centro, Integer> {
    
    List<Centro> findAll();
}
