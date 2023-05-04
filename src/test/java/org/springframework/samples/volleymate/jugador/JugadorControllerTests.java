package org.springframework.samples.volleymate.jugador;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.volleymate.aspecto.AspectoService;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorController;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.logro.LogroService;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.samples.volleymate.valoracion.ValoracionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = JugadorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class JugadorControllerTests {

	private static final int TEST_Jugador_ID = 10;

	@MockBean
	private JugadorService jugadorService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private LogroService logrosService;

	@MockBean
	private PartidoService partidaService;

	@MockBean
	private SolicitudService solicitudService;

	@MockBean
	private ValoracionService valoracionService;

	@MockBean
	private AspectoService aspectoService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		Jugador george = new Jugador();
		User user = new User();
		Authorities rol = new Authorities();
		rol.setAuthority("jugador");
		user.setEnabled(true);
		user.setUsername("test");
		user.setPassword("123");
		user.setCorreo("test@gmail.com");
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setId(10);
		george.setVolleys(150);
		george.setTelephone("657454545");
		george.setCiudad(Ciudad.SEVILLA);
		george.setSexo(Sexo.MASCULINO);
		george.setPremium(false);
		george.setImage("/resources/images/perfilPorDefecto.png");
		given(this.jugadorService.findJugadorByUsername("test")).willReturn(george);

		Jugador admin = new Jugador();
		User adminUser = new User();
		Authorities rolAdmin = new Authorities();
		rolAdmin.setAuthority("admin");
		adminUser.setEnabled(true);
		adminUser.setUsername("testAdmin");
		adminUser.setPassword("123");
		admin.setFirstName("Admin");
		admin.setLastName("Test");
		admin.setVolleys(150);
		admin.setVolleys(150);
		admin.setTelephone("657454545");
		admin.setCiudad(Ciudad.SEVILLA);
		admin.setSexo(Sexo.MASCULINO);
		admin.setPremium(false);
		admin.setImage("/resources/images/perfilPorDefecto.png");
		given(this.jugadorService.findJugadorByUsername("testAdmin")).willReturn(admin);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/jugadores/new"))
		.andExpect(model().attributeExists("jugador"))
		.andExpect(status().isOk())
				.andExpect(view().name("jugadores/crearJugador"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/jugadores/new")
		.param("firstName", "Joe")
		.param("lastName", "Bloggs")
		.param("user.username","aaa")
		.param("user.password","123")
		.param("user.enables", "true")
		.param("user.authorities","jugador")
		.param("telephone","654667788")
		.param("volleys","150")
		.param("image","/resources/images/perfilPorDefecto.png")
		.with(csrf())
				).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/jugador/new").with(csrf())
		.param("firstName", "")
		.param("lastName", "Bloggs")
		.param("user.username","aaa")
		.param("user.password","123")
		.param("user.enables", "true")
		.param("user.authorities","jugador")
		)
		.andExpect(status().isOk()).andExpect(model().attributeHasErrors("jugador"))	
		.andExpect(view().name("jugador/createOrUpdateJugadorForm"));
	}

	//Test Actualizar datos juagdor
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateJugadorForm() throws Exception {
		mockMvc.perform(get("/jugador/edit/{id}", TEST_Jugador_ID)).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateJugadorFormSuccess() throws Exception {
		mockMvc.perform(post("/jugador/new")
		.param("firstName", "Holaa")
		.param("lastName", "Testt")
		.with(csrf())
				).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateJugadorFormHasErrors() throws Exception {
		mockMvc.perform(post("/jugador/edit/{id}", TEST_Jugador_ID).with(csrf()).param("firstName", "")
				.param("lastName", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("jugador"))
				.andExpect(model().attributeHasFieldErrors("jugador", "firstName"))
				.andExpect(model().attributeHasFieldErrors("jugador", "lastName"))
				.andExpect(view().name("jugador/createOrUpdateJugadorForm"));
	}

	//Ver tu propio perfil de jugador
	@WithMockUser(value = "spring", username = "test", authorities = "jugador")
	@Test
	void testShowJugador() throws Exception {
		mockMvc.perform(get("/jugadores"))
				.andExpect(status().isOk())
				.andExpect(view().name("jugadores/detallesJugador"));
	}

	//Ver el perfil de otro jugador
	@WithMockUser(value = "spring", username = "test", authorities = "jugador")
	@Test
	void testShowJugadorAOtroJugador() throws Exception {
		mockMvc.perform(get("/jugadores/" + TEST_Jugador_ID))
				.andExpect(status().isOk())
				.andExpect(view().name("jugadores/detallesJugador"));
	}
}