package org.springframework.samples.volleymate.partido;

import java.util.List;

import org.springframework.dao.DataAccessException;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface PartidoRepository extends CrudRepository<Partido, Integer> {
    List<Partido> findAll();

    @Query("SELECT P FROM Partido P")
    List<Partido> findAllPageable(Pageable pageable);

    
    Partido findById(int id) throws DataAccessException;

    @Query("SELECT P FROM Partido P WHERE P.creador.id =:id")
	Set<Partido> getPartidosByCreatorId(@Param("id") int id);

    @Query("SELECT p FROM Partido p WHERE p.tipo =:tipo")
    Set<Partido> findPartidosByTipo(Tipo tipo);

    @Query("SELECT p FROM Partido p WHERE p.sexo =:sexo")
    Set<Partido> findPartidosBySexo(Sexo sexo);


}
