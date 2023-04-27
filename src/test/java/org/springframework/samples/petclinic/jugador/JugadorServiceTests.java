package org.springframework.samples.petclinic.jugador;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.samples.volleymate.VolleyMateApplication;
import org.springframework.samples.volleymate.configuration.TestDatabaseConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest(classes = VolleyMateApplication.class)
//@ActiveProfiles("test")

@SpringBootTest(classes = {VolleyMateApplication.class, TestDatabaseConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JugadorServiceTests {

	@Autowired
	protected JugadorService jugadorService;

	

	@BeforeEach
	void setup() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123");
		user.setEnabled(true);
		user.setCorreo("test@test.com");

		Authorities rol = new Authorities();
		rol.setAuthority("jugador");
        rol.setUser(user);

		Jugador george = new Jugador();
		george.setFirstName("George");
		george.setLastName("Davis");
		george.setUser(user);
        george.setVolleys(200);
        george.setSexo(Sexo.MASCULINO);
        		

		jugadorService.saveJugador(george);

	}

	@Test
	public void shouldFindAllJugadores() {
		List<Jugador> jugadores = jugadorService.findAll();
		assertThat(jugadores).isNotNull();
		assertThat(jugadores.size()).isEqualTo(1);
	}

	@Test
	public void shouldFindJugadorById() {
		Jugador jugador = jugadorService.findJugadorById(1);
		assertThat(jugador).isNotNull();
		assertThat(jugador.getFirstName()).isEqualTo("George");
	}

	@Test
	public void shouldFindJugadorByUsername() {
		Jugador jugador = jugadorService.findJugadorByUsername("Test");
		assertThat(jugador).isNotNull();
		assertThat(jugador.getFirstName()).isEqualTo("George");
	}
}

