package org.springframework.samples.volleymate.jugador;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.aspecto.Aspecto;
import org.springframework.samples.volleymate.aspecto.AspectoService;
import org.springframework.samples.volleymate.centro.Centro;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorController;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.logro.Logro;
import org.springframework.samples.volleymate.logro.LogroService;
import org.springframework.samples.volleymate.mensaje.Mensaje;
import org.springframework.samples.volleymate.mensaje.MensajeService;
import org.springframework.samples.volleymate.pagos.PaymentService;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoPageService;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.samples.volleymate.valoracion.ValoracionService;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = JugadorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
    "database=hsqldb",
"spring.h2.console.enabled=true" })
class JugadorControllerTests {

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
		george.setCiudad(Ciudad.BADAJOZ);
		george.setFechaFinPremium(LocalDateTime.parse("2024-01-01T00:00:00"));
		george.setFechaInicioPremium(LocalDateTime.parse("2020-01-01T00:00:00"));

		george.setImage("'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg?w=2000'");
		george.setLogros(new ArrayList<Logro>());
		george.setAspectos(new ArrayList<Aspecto>());
		george.setPremium(false);
		george.setTelephone("123456789");
		george.setPartidos(new HashSet<Partido>());


		given(this.jugadorService.findJugadorById(1)).willReturn(george);
		given(this.jugadorService.findJugadorByUsername("Test")).willReturn(george);
		Page<Partido> paginaVacia = new PageImpl<>(Collections.emptyList());
		given(this.partidoService.buscarPartidosPorJugador(0, george)).willReturn(paginaVacia);
		given(this.jugadorService.findPartidosByJugadorId(1)).willReturn(new ArrayList<Partido>());
		given(this.solicitudService.findSolicitudesATusPartidos(george)).willReturn(new HashSet<Solicitud>());
		given(this.solicitudService.findTusSolicitudes(george)).willReturn(new HashSet<Solicitud>());

		given(this.jugadorService.listAll(null, 0)).willReturn(new ArrayList<>(Arrays.asList(george)));

		

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

		given(this.jugadorService.findJugadorById(2)).willReturn(barba);
		given(this.jugadorService.findJugadorByUsername("Test2")).willReturn(barba);

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

		given(this.jugadorService.findJugadorById(3)).willReturn(jugador3);
		given(this.jugadorService.findJugadorByUsername("Test3")).willReturn(jugador3);
		given(this.aspectoService.findAllAspectos()).willReturn(new ArrayList<Aspecto>());
		given(this.jugadorService.esAdmin(jugador3)).willReturn(true);

	}

	@WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/jugadores/new")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/crearJugador"));
	}

	/*@WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/jugadores/new")
		.param("firstName", "Joe")
		.param("lastName", "Bloggs")
		.param("sexo", "MASCULINO")
		.param("fechaNacimiento", "1999/01/01")
		.param("volleys", "200")
		.param("telephone", "123456789")
		.param("ciudad", "SEVILLA")
		.param("user.username", "Test4")
		.param("user.password", "123")
		.param("user.correo", "test4@us.es")
		.param("user.enabled", "true")
		.param("user.authorities", "jugador")).with(csrf())
				.andExpect(status().isOk());
	}*/
				


    @WithMockUser(value = "spring", username = "Test3", authorities = "jugador")
    @Test
    void testPerfil() throws Exception {
        mockMvc.perform(get("/jugadores/1")).andExpect(status().isOk())
                .andExpect(view().name("jugadores/detallesJugador"));
    }

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowJugadorAutenticado() throws Exception {
		mockMvc.perform(get("/jugadores")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/detallesJugador"));
	}

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowMisPartidos() throws Exception {
		mockMvc.perform(get("/jugadores/mispartidos")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/misPartidos"));
	}

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowVistaNotificaciones() throws Exception {
		mockMvc.perform(get("/jugadores/notificaciones")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/notificacionesJugador"));
	}

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowJugadores() throws Exception {
		mockMvc.perform(get("/listaJugadores")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/listaJugadores"));
	}

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowVistaMisAspectos() throws Exception {
		mockMvc.perform(get("/misAspectos")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/listaMisAspectos"));
	}

	@WithMockUser(value = "spring", username = "Test", authorities = "jugador")
    @Test
	void testShowTerminos() throws Exception {
		mockMvc.perform(get("/terminos")).andExpect(status().isOk())
				.andExpect(view().name("jugadores/terminos"));
	}

	

	

}
