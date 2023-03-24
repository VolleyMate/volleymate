package org.springframework.samples.volleymate.partido;

import java.util.List;

import org.springframework.dao.DataAccessException;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CentroRepository extends CrudRepository<Centro, Integer> {
    
    List<Centro> findAll();
}
