package org.springframework.samples.volleymate.valoracion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.User;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ValoracionServiceTest {
	
	@Autowired
	protected JugadorService jugadorService;
	
	@Autowired
	protected ValoracionService valoracionService;
	
	@MockBean
    private JavaMailSender mailSender;

	@BeforeEach
	void setup() {
		
		// ==== Jugador1 ==== //

		User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);
		user2.setCorreo("test@test.com");

		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
        user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));
		

		Jugador jugador1 = new Jugador();
		jugador1.setFirstName("Jugador1");
		jugador1.setLastName("Apellido1");
		jugador1.setUser(user2);
		jugador1.setVolleys(200);
		jugador1.setSexo(Sexo.MASCULINO);
		
		jugadorService.saveJugador(jugador1);

		// ==== Jugador2 ==== //
		User user3 = new User();
		user3.setUsername("Test3");
		user3.setPassword("123");
		user3.setEnabled(true);
		user3.setCorreo("test3@test.com");

		Authorities rol3 = new Authorities();
		rol3.setAuthority("jugador");
		user3.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol3)));

		Jugador jugador2 = new Jugador();
		jugador2.setFirstName("Jugador2");
		jugador2.setLastName("Apellido2");
		jugador2.setUser(user3);
		jugador2.setVolleys(200);
		jugador2.setSexo(Sexo.MASCULINO);

		jugadorService.saveJugador(jugador2);
		
		// ==== Valoracion1 ==== //
		Valoracion val1 = new Valoracion();
		val1.setPuntuacion(4);
		val1.setRatedPlayer(jugador2);
		val1.setRatingPlayer(jugador1);
		val1.setComentario("");

		valoracionService.saveValoracion(val1);
		
		// ==== Valoracion2 ==== //
		Valoracion val2 = new Valoracion();
		val2.setPuntuacion(4);
		val2.setRatedPlayer(jugador1);
		val2.setRatingPlayer(jugador2);
		val2.setComentario("Comentario1");
		
		valoracionService.saveValoracion(val2);
		
	}
	
	@Test
	public void shouldFindValoracionesByJugadorId() {
		Valoracion valoracion = valoracionService.findValoracionesByJugadorId(1).get(0);
		assertThat(valoracion.getRatedPlayer().getId()==1);
	}
	
	@Test
	public void shouldCheckValoracionExiste() {
		assertThat(valoracionService.valoracionExiste(1, 2));
	}

}
