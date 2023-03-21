package org.springframework.samples.volleymate.valoracion;

import java.util.List;

import org.springframework.dao.DataAccessException;


import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ValoracionRepository extends CrudRepository<Valoracion, Integer> {
   
    
}
