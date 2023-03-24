package org.springframework.samples.volleymate.valoracion;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;


import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ValoracionRepository extends CrudRepository<Valoracion, Integer> {

    @Query("SELECT count(*) FROM Valoracion v WHERE v.ratedPlayer.id = ?1 and v.ratingPlayer.id = ?2")
    Integer valoracionExiste(Integer ratingPlayerId, Integer ratedPlayerId);

    @Query("SELECT v FROM Valoracion v WHERE v.ratedPlayer.id = ?1")
    List<Valoracion> findValoracionesByJugadorId(Integer jugadorId);
   
    
}
