package org.springframework.samples.petclinic.mensaje;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.mensaje.Mensaje;
import org.springframework.samples.volleymate.mensaje.MensajeService;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.partido.Tipo;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MensajeServiceTests {

    @Autowired
    protected MensajeService mensajeService;

    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthoritiesService authoritiesService;

    @Autowired
    protected PartidoService partidoService;

    @BeforeEach
    void setUp() {

        // ==== User3 ==== //
        User user3 = new User();
		user3.setUsername("Test3");
		user3.setPassword("123");
		user3.setEnabled(true);
		user3.setCorreo("test3@test.com");

        // ==== Authorities ==== //
		Authorities rol3 = new Authorities();
		rol3.setAuthority("jugador");
		user3.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol3)));

        // ==== Jugador3 ==== //
		Jugador jugador3 = new Jugador();
        jugador3.setId(3);
		jugador3.setFirstName("Jugador3");
		jugador3.setLastName("Davis");
		jugador3.setUser(user3);
		jugador3.setVolleys(200);
		jugador3.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador3);
        userService.saveUser(user3);
        authoritiesService.saveAuthorities(user3.getUsername(), "jugador");

        // ==== User2 ==== //
        User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);
		user2.setCorreo("test2@test.com");

        // ==== Authorities ==== //
		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
		user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));

        // ==== Jugador3 ==== //
		Jugador jugador2 = new Jugador();
		jugador2.setFirstName("Jugador2");
		jugador2.setLastName("Gomez");
		jugador2.setUser(user2);
		jugador2.setVolleys(200);
		jugador2.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador2);
        userService.saveUser(user2);
        authoritiesService.saveAuthorities(user2.getUsername(), "jugador");

        // ==== Partido 1 ==== //
        Partido partido = new Partido();
        partido.setId(1);
        partido.setNombre("Nombre prueba");
        partido.setDescripcion("Desc prueba");
        partido.setTipo(Tipo.VOLEIBOL);
        partido.setCreador(jugador3);
        partido.setNumJugadoresNecesarios(2);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        partido.setJugadores(jugadores);

        // === Mensaje 1 === //
        Mensaje mensaje = new Mensaje();
        mensaje.setContenidoMensaje("Prueba");
        mensaje.setEmisor(jugador3);
        mensaje.setPartido(partido);
        mensajeService.save(mensaje);

        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(mensaje);
        partido.setMensajes(mensajes);
        partidoService.save(partido);
    }

    @Test
    public void shouldFindAllMensajesByPartidoId() {
        Set<Mensaje> mensajes = mensajeService.findAllMensajesByPartidoId(1);
        assertThat(mensajes).isNotNull();
        assertThat(mensajes.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindTusMensajes() {
        Set<Mensaje> mensajes = mensajeService.findTusMensajes(jugadorService.findJugadorById(3));
        assertThat(mensajes).isNotNull();
        assertThat(mensajes.size()).isEqualTo(1);
    }

    @Test
    public void shouldGetParseoMensaje() {
        String mensaje = "Esto es un mensaje DE PrUeBa";
        List<String> lista = mensajeService.getParseoMensaje(mensaje);
        assertThat(lista).isNotNull();
        assertThat(lista.size()).isEqualTo(6);
    }

    @Test
    public void shouldGetListaDeInsultos() {
        List<String> lista = mensajeService.getListaDeInsultos();
        assertThat(lista).isNotNull();
        assertThat(lista.size()).isEqualTo(12);
    }
    
}
