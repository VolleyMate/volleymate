package org.springframework.samples.volleymate.valoracion;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.User;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ValoracionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
	    "database=hsqldb",
	"spring.h2.console.enabled=true" })
public class ValoracionControllerTest {

	@MockBean
	protected JugadorService jugadorService;
	
	@MockBean
	protected ValoracionService valoracionService;
	
    @Autowired
    private MockMvc mockMvc;
	
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
	
//	@WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
//    @Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/valoraciones/new/"+1)).andExpect(status().isOk())
//				.andExpect(view().name("valoraciones/crearValoracion"));
//	}
	
	//processCreationFormprocessCreationForm
	
	//showValoracionesJugador
	@WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
    @Test
	void testShowValoracionesJugador() throws Exception {
		mockMvc.perform(get("/valoraciones/"+1)).andExpect(status().isOk())
				.andExpect(view().name("valoraciones/valoracionesJugador"));
	}
}
