package org.springframework.samples.volleymate.jugador;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface JugadorRepository extends CrudRepository<Jugador, Integer> {
    
    @Query("SELECT j FROM Jugador j WHERE j.id=:id")
    public Jugador findById(@Param("id") int id);

    @Query("select j from Jugador j where j.user.username = ?1")
    public Jugador findByUsername(String username);

    @Query("select j from Jugador j where j.user.correo = ?1")
    public Jugador findByCorreo(String correo);

    @Query("SELECT j FROM Jugador j WHERE CONCAT(j.user.username,j.ciudad) LIKE %?1%")
    public List<Jugador> findAll(String palabraClave);

    @Query("SELECT j FROM Jugador j")
    public List<Jugador> findAll();

}
