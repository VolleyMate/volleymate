package org.springframework.samples.volleymate.jugador;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.aspecto.AspectoService;
import org.springframework.samples.volleymate.centro.Centro;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorController;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.logro.LogroService;
import org.springframework.samples.volleymate.mensaje.Mensaje;
import org.springframework.samples.volleymate.mensaje.MensajeService;
import org.springframework.samples.volleymate.pagos.PaymentService;
import org.springframework.samples.volleymate.partido.PartidoPageService;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.samples.volleymate.valoracion.ValoracionService;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = JugadorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
    "spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JugadorControllerTests {

    @MockBean
	private JugadorService jugadorService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AspectoService aspectoService;

    @MockBean
    private PartidoService partidoService;

    @MockBean
    private SolicitudService solicitudService;
    
    @MockBean
    private ValoracionService valoracionService;
    
    @MockBean
    private AuthoritiesService authoritiesService;
    



    @BeforeEach
	void setup() {

        // ==== Jugador1 ==== //
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123");
		user.setEnabled(true);
		user.setCorreo("test@test.com");

		Authorities rol = new Authorities();
		rol.setAuthority("admin");
        user.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol)));

        userService.saveUser(user);


		Jugador george = new Jugador();
		george.setFirstName("George");
		george.setLastName("Davis");
		george.setUser(user);
        george.setVolleys(200);
        george.setSexo(Sexo.MASCULINO);
		

		// ==== Jugador2 ==== //

		User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);
		user2.setCorreo("test@test.com");

		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
        user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));
		

		Jugador barba = new Jugador();
		barba.setFirstName("Barba");
		barba.setLastName("Davis");
		barba.setUser(user2);
        barba.setVolleys(200);
        barba.setSexo(Sexo.MASCULINO);

		// ==== Jugador3 ==== //
		User user3 = new User();
		user3.setUsername("Test3");
		user3.setPassword("123");
		user3.setEnabled(true);
		user3.setCorreo("test3@test.com");

		Authorities rol3 = new Authorities();
		rol3.setAuthority("jugador");
		user3.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol3)));

		Jugador jugador3 = new Jugador();
		jugador3.setFirstName("Jugador3");
		jugador3.setLastName("Davis");
		jugador3.setUser(user3);
		jugador3.setVolleys(200);
		jugador3.setSexo(Sexo.MASCULINO);

        userService.saveUser(user);
        userService.saveUser(user2);
        userService.saveUser(user3);
		jugadorService.saveJugador(jugador3);
		jugadorService.saveJugador(barba);
		jugadorService.saveJugador(george);

	}

    @WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
    @Test
    void testPerfil() throws Exception {
        mockMvc.perform(get("/jugadores/1")).andExpect(status().isOk())
                .andExpect(view().name("jugadores/detallesJugador"));
    }
}
