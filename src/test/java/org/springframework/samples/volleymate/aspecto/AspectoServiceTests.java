package org.springframework.samples.volleymate.aspecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.Authorities;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AspectoServiceTests {
    
    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    protected AspectoService aspectoService;

    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthoritiesService authoritiesService;

    @BeforeEach
    void setUp() {

        // ==== User3 ==== //
        User user3 = new User();
		user3.setUsername("Test3");
		user3.setPassword("123");
		user3.setEnabled(true);

        // ==== Authorities ==== //
		Authorities rol3 = new Authorities();
		rol3.setAuthority("jugador");
		user3.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol3)));

        // ==== Jugador3 ==== //
		Jugador jugador3 = new Jugador();
		jugador3.setFirstName("Jugador3");
		jugador3.setLastName("Davis");
		jugador3.setUser(user3);
		jugador3.setVolleys(200);
		jugador3.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador3);
        userService.saveUser(user3);
        authoritiesService.saveAuthorities(user3.getUsername(), "jugador");

        // ==== Aspecto1 ==== //
        Aspecto aspecto1 = new Aspecto();
        aspecto1.setImagen("https://img.freepik.com/vector-premium/caricatura-persona-sosteniendo-pelota_494525-740.jpg?size=626&ext=jpg");
        aspecto1.setPrecio(250);
        List<Jugador> jugadores1 = new ArrayList<>();
        jugadores1.add(jugador3);
        aspecto1.setJugadores(jugadores1);
        aspectoService.save(aspecto1);

        Aspecto aspecto2 = new Aspecto();
        aspecto2.setImagen("https://img.freepik.com/vector-premium/caricatura-persona-sosteniendo-pelota_494525-740.jpg?size=626&ext=jpg");
        aspecto2.setPrecio(0);
        List<Jugador> jugadores2 = new ArrayList<>();
        jugadores2.add(jugador3);
        aspecto2.setJugadores(jugadores2);
        aspectoService.save(aspecto2);
    }

    @Test
    public void shouldFindAspectoById() {
        Aspecto aspecto = aspectoService.findById(1);
        assertThat(aspecto).isNotNull();
        assertThat(aspecto.getPrecio()).isEqualTo(250);
    }

    @Test
    public void shouldFindAllAspectosGratuitos() {
        List<Aspecto> aspectosG = aspectoService.findAllAspectosGratuitos();
        assertThat(aspectosG).isNotNull();
        assertThat(aspectosG.size()).isEqualTo(1);
    }

    @Test
    public void shouldValidarAspecto() {
        Aspecto aspecto = aspectoService.findById(1);
        List<String> errores = aspectoService.validarAspecto(aspecto);
        assertThat(errores).isNotNull();
        assertThat(errores.size()).isEqualTo(0);
    }

}
