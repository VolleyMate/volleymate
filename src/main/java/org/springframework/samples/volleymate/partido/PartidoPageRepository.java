package org.springframework.samples.volleymate.partido;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartidoPageRepository extends PagingAndSortingRepository<Partido, Long> {

  Page<Partido> findAll(Pageable pageable);

  Page<Partido> findBySexo(Pageable pageable, Sexo sexo);
  
  Page<Partido> findByTipo(Pageable pageable, Tipo tipo);

  Page<Partido> findBySexoAndTipo(Pageable pageable, Sexo sexo, Tipo tipo);

  Page<Partido> findByJugadoresId(Pageable pageable, int id);

  Page<Partido> findByFechaAfter(Pageable pageable, LocalDateTime fecha);

  Page<Partido> findBySexoAndFechaAfter(Pageable pageable, Sexo sexo, LocalDateTime fecha);

  Page<Partido> findByTipoAndFechaAfter(Pageable pageable, Tipo tipo, LocalDateTime fecha);

  Page<Partido> findBySexoAndTipoAndFechaAfter(Pageable pageable, Sexo sexo, Tipo tipo, LocalDateTime fecha);

}