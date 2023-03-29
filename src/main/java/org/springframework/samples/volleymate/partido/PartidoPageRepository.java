package org.springframework.samples.volleymate.partido;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartidoPageRepository extends PagingAndSortingRepository<Partido, Long> {

  Page<Partido> findAll(Pageable pageable);

  Page<Partido> findBySexo(Pageable pageable, Sexo sexo);
  
  Page<Partido> findByTipo(Pageable pageable, Tipo tipo);

  //Page<Partido> findByCiudad(Pageable pageable, String ciudad);

  Page<Partido> findBySexoAndTipo(Pageable pageable, Sexo sexo, Tipo tipo);

  //Page<Partido> findBySexoAndCiudad(Pageable pageable, Sexo sexo, String ciudad);

  //Page<Partido> findByTipoAndCiudad(Pageable pageable, Tipo tipo, String ciudad);

  //Page<Partido> findBySexoAndTipoAndCiudad(Pageable pageable, Sexo sexo, Tipo tipo, String ciudad);
}