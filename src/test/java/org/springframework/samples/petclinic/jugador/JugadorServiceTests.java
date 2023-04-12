package org.springframework.samples.petclinic.jugador;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class JugadorServiceTests {

    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected PartidoService partidoService;

    @Test
    public void shouldFindJugadorByUsername() {
        Jugador jugador = this.jugadorService.findJugadorByUsername("jorsilman");
        assertEquals(jugador.getFirstName(), "Jorge");

        Jugador jugador2 = this.jugadorService.findJugadorByUsername("barba");
        assertEquals(jugador2.getFirstName(), "Francisco Javier");
    }

    @Test
    @Transactional
    public void shouldInsertJugador() {
        Jugador jugador = new Jugador();
        jugador.setCiudad("Sevilla");
        jugador.setFirstName("Pablo");
        jugador.setLastName("Ruiz");
        jugador.setSexo(Sexo.MASCULINO);
        jugador.setTelephone(612345678);
            User user = new User();
            user.setUsername("pabloruiz");
            user.setPassword("pabloruiz4");
            user.setEnabled(true);
            user.setCorreo("pabloruiz@gmail.com");
            jugador.setUser(user);

        this.jugadorService.saveJugador(jugador);
        assertNotEquals(jugador.getId().longValue(), 0);

        assertEquals(jugadorService.findJugadorByUsername("pabloruiz"), jugador);
    }

    @Test
    public void shouldUnirsePartida() throws YaUnidoException {
        Partido partido = this.partidoService.findPartidoById(7);
        Integer jugadores = partido.getJugadores().size();
        Jugador jugador = this.jugadorService.findJugadorById(2);
        this.jugadorService.unirsePartida(jugador.getId(), partido.getId());
        assertEquals(partido.getJugadores().size(), jugadores+1);
    }
    
}
