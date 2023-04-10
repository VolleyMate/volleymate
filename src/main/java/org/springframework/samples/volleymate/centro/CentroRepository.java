package org.springframework.samples.volleymate.centro;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CentroRepository extends CrudRepository<Centro, Integer> {
    
    List<Centro> findAll();

    @Query("SELECT c FROM Centro c WHERE c.estado = true")
    List<Centro> findAcceptedCentros();

    @Query("SELECT c FROM Centro c WHERE c.estado = true")
    Page<Centro> findAcceptedCentrosPageable(Pageable pageable);


    @Query("SELECT c FROM Centro c WHERE c.estado = false")
    List<Centro> getSolicitudesCentros();

    @Query("SELECT c FROM Centro c WHERE c.id = ?1")
    Centro getCentroById(int centroId);

    @Query("DELETE FROM Centro c WHERE c.id = ?1")
    void deleteCentroById(int centroId);
}
