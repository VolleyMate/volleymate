package org.springframework.samples.volleymate.jugador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.centro.Centro;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.partido.Tipo;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JugadorServiceTests {

	@Autowired
	protected JugadorService jugadorService;

	@Autowired
	protected PartidoService partidoService;

	@Autowired
	protected CentroService centroService;

	@MockBean
    private JavaMailSender mailSender;

	@Autowired
	protected SolicitudService solicitudService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected AuthoritiesService authoritiesService;



	@BeforeEach
	void setup() {
		

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

		jugadorService.saveJugador(jugador3);
		

		
		// ==== Centro ==== //
		
		Centro centro = new Centro();
		centro.setNombre("Centro de prueba");
		centro.setCiudad("Sevilla");
		centro.setDireccion("Dirrección de prueba");
		centro.setEstado(true);
		centro.setMaps("https://goo.gl/maps/P5uyWUKnDqNLAbnE6");
		
		Partido partido = new Partido();
		partido.setNombre("Partido de prueba");
		partido.setSexo(org.springframework.samples.volleymate.partido.Sexo.MASCULINO);
		partido.setDescripcion("Descripción de prueba");
		partido.setTipo(Tipo.VOLEIBOL);
		partido.setCentro(centro);
		partido.setFecha(LocalDateTime.of(2024, 6, 12, 12, 0, 0));
		partido.setFechaCreacion(LocalDateTime.of(2024, 6, 11, 12, 0, 0));
		partido.setPrecioPersona(100);
		partido.setNumJugadoresNecesarios(5);
		partido.setCreador(barba);
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.add(barba);
		partido.setJugadores(jugadores);

		
		
		centroService.saveCentro(centro);
		partidoService.save(partido);

		// ==== Jugador1 ==== //
		User user = new User();
		user.setUsername("Test");
		user.setPassword("123");
		user.setEnabled(true);
		user.setCorreo("test@test.com");

		Authorities rol = new Authorities();
		rol.setAuthority("admin");
        user.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol)));


		Jugador george = new Jugador();
		george.setFirstName("George");
		george.setLastName("Davis");
		george.setUser(user);
        george.setVolleys(200);
        george.setSexo(Sexo.MASCULINO);
		Set<Partido> partidos = new HashSet<Partido>();
		partidos.add(partido);
		george.setPartidos(partidos);

		Partido partido2 = new Partido();
		partido2.setNombre("Partido de prueba 2");
		partido2.setSexo(org.springframework.samples.volleymate.partido.Sexo.MASCULINO);
		partido2.setDescripcion("Descripción de prueba 2");
		partido2.setTipo(Tipo.VOLEIBOL);
		partido2.setCentro(centro);
		partido2.setFecha(LocalDateTime.of(2024, 6, 12, 12, 0, 0));
		partido2.setFechaCreacion(LocalDateTime.of(2024, 6, 11, 12, 0, 0));
		partido2.setPrecioPersona(100);
		partido2.setNumJugadoresNecesarios(5);
		partido2.setCreador(george);
		List<Jugador> jugadores2 = new ArrayList<Jugador>();
		jugadores2.add(jugador3);
		partido2.setJugadores(jugadores2);
		partidoService.save(partido2);

		
		jugadorService.saveJugador(barba);
		jugadorService.saveJugador(george);

	}

	@Test
	public void shouldFindAllJugadores() {
		List<Jugador> jugadores = jugadorService.findAll();
		assertThat(jugadores).isNotNull();
		assertThat(jugadores.size()).isEqualTo(3);
	}

	@Test
	public void shouldFindJugadorById() {
		Jugador jugador = jugadorService.findJugadorById(2);
		assertThat(jugador).isNotNull();
		assertThat(jugador.getFirstName()).isEqualTo("Barba");
	}

	@Test
	public void shouldFindJugadorByUsername() {
		Jugador jugador = jugadorService.findJugadorByUsername("Test");
		assertThat(jugador).isNotNull();
		assertThat(jugador.getFirstName()).isEqualTo("George");
	}

	@Test
	public void shouldUnirseAPartido() throws YaUnidoException{
		Jugador jugador = jugadorService.findJugadorByUsername("Test2");
		Partido partido = partidoService.findPartidoById(2);
		jugadorService.unirsePartida(jugador.getId(), partido.getId());
		Partido partido2 = partidoService.findPartidoById(1);
		assertThat(partido2.getJugadores().size()).isEqualTo(1);
	}

	@Test
	public void shouldCrearEliminarSolicitud(){
		Jugador jugador = jugadorService.findJugadorByUsername("Test");
		Partido partido = partidoService.findPartidoById(1);
		jugadorService.crearSolicitudPartido(jugador, partido);
		assertThat(solicitudService.findAllSolicitudesByPartidoId(partido.getId()).size()).isEqualTo(1);
		Set<Solicitud> solicitudes = solicitudService.findAllSolicitudesByPartidoId(partido.getId());
		List<Solicitud> listaSolicitudes = new ArrayList<>(solicitudes);
		jugadorService.eliminarSolicitud(listaSolicitudes.get(0));
		assertThat(solicitudService.findAllSolicitudesByPartidoId(partido.getId()).size()).isEqualTo(0);
	}

	@Test
	public void shoulFindPartidosByJugadorId(){
		assertThat(jugadorService.findPartidosByJugadorId(2).size()).isEqualTo(1);
	}

	@Test
	public void testFindErroresCrearJugador() {
		// Preparación
		Jugador jugador = new Jugador();
		jugador.setFirstName("Juan");
		jugador.setLastName("García");
		jugador.setTelephone("12345678");
		User user = new User();
		user.setCorreo("juan.garcia@example.com");
		jugador.setUser(user);

		// Ejecución
		List<String> errores = jugadorService.findErroresCrearJugador(jugador);

		// Comprobación
		assertEquals(1, errores.size());
		assertEquals("El teléfono debe tener 9 cifras", errores.get(0));

		Jugador jugador2 = new Jugador();
		jugador2.setFirstName("Juan");
		jugador2.setLastName("García");
		jugador2.setTelephone("657236154");
		User user2 = new User();
		user2.setCorreo("juan.garci");
		jugador2.setUser(user2);

		// Ejecución
		List<String> errores2 = jugadorService.findErroresCrearJugador(jugador2);

		assertEquals(1, errores2.size());
		assertEquals("El correo no es válido", errores2.get(0));

		
	}

	@Test
	public void esAdminTest(){
		Jugador jugador = jugadorService.findJugadorByUsername("Test");
		assertThat(jugadorService.esAdmin(jugador)).isEqualTo(true);
		Jugador jugador2 = jugadorService.findJugadorByUsername("Test2");
		assertThat(jugadorService.esAdmin(jugador2)).isEqualTo(false);
	}
	
}

