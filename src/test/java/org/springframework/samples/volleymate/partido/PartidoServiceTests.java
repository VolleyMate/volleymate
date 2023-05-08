package org.springframework.samples.volleymate.partido;


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
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PartidoServiceTests {
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
    public void setUp(){

        User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);
		user2.setCorreo("test@test.com");

		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
        user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));
		

		Jugador alejandro = new Jugador();
		alejandro.setFirstName("Barba");
		alejandro.setLastName("Davis");
		alejandro.setUser(user2);
        alejandro.setVolleys(200);
        alejandro.setSexo(org.springframework.samples.volleymate.jugador.Sexo.MASCULINO);

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
		jugador3.setSexo(org.springframework.samples.volleymate.jugador.Sexo.MASCULINO);

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
		partido.setSexo(Sexo.MASCULINO);
        partido.setDescripcion("Descripción de prueba");
		partido.setTipo(Tipo.VOLEIBOL);
		partido.setCentro(centro);
		partido.setFecha(LocalDateTime.of(2024, 6, 12, 12, 0, 0));
		partido.setFechaCreacion(LocalDateTime.of(2024, 6, 11, 12, 0, 0));
		partido.setPrecioPersona(100);
		partido.setNumJugadoresNecesarios(5);
		partido.setCreador(alejandro);
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.add(alejandro);
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
        george.setSexo(org.springframework.samples.volleymate.jugador.Sexo.MASCULINO);
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

		
		jugadorService.saveJugador(alejandro);
		jugadorService.saveJugador(george);
    }

    @Test
	public void shouldFindAllPartidos() {
		List<Partido> partidos = partidoService.findAllPartidos();
		assertThat(partidos).isNotNull();
		assertThat(partidos.size()).isEqualTo(2);
	}

	@Test
	public void shouldFindPartidoById() {
		Partido partido = partidoService.findPartidoById(2);
		assertThat(partido).isNotNull();
		assertThat(partido.getDescripcion().equals("Descripción de prueba 2"));
	}

	@Test
	public void shouldFindPartidoByJugador() {
		Jugador jugador = jugadorService.findJugadorByUsername("Test3");
		Page<Partido> partidos = partidoService.buscarPartidosPorJugador(0, jugador);
		assertThat(partidos).isNotNull();
		assertThat(partidos.getSize()).isEqualTo(6);
	}

	@Test
	public void shouldFiltrarPartidos() {
		Page<Partido> partidos = partidoService.filtrarPartidos(org.springframework.samples.volleymate.partido.Sexo.MASCULINO, Tipo.VOLEIBOL, 0);
		assertThat(partidos).isNotNull();
		assertThat(partidos.getSize()).isEqualTo(6);
	}

	@Test
	public void shouldFindCiudades() {
		Set<String> ciudades = partidoService.getCiudades();
		assertThat(ciudades).isNotNull();
		assertThat(ciudades.size()).isEqualTo(1);
	}

	// @Test
	// public void shouldFindJugadorDentroPartido() {
	// 	Jugador jugador = jugadorService.findJugadorByUsername("Test3");
	// 	Boolean estaDentro = partidoService.getJugadorDentroPartido(2, jugador);
	// 	assertThat(ciudades).isNotNull();
	// 	assertThat(ciudades.size()).isEqualTo(1);
	// }

	// @Test
	// public void shouldDeletePartido(){
	// 	List<Partido> partidos = partidoService.findAllPartidos();
	// 	Partido partido = partidos.get(0);
	// 	assertThat(partido).isNotNull();
	// 	partidoService.deletePartido(partido);
	// 	// assertThat(partidoService.findAllPartidos().size()).isEqualTo(1);
	// }

	

// 	@Transactional
// 	public void deletePartido(@Valid Partido partido) throws DataAccessException, DataIntegrityViolationException {

// 		for (Jugador jugador : partido.getJugadores()) {
// 			if(!partido.getCreador().equals(jugador)){
// 				jugador.setVolleys(jugador.getVolleys() + 150);
// 			}
// 			jugador.getPartidos().remove(partido);
// 		}
// 		partidoRepository.delete(partido);
// 	}

// 	public Partido findPartidoById(int id) throws DataAccessException{
// 		return partidoRepository.findById(id);
// 	}
	
//   	public Set<Partido> getPartidosByCreatorId(Integer id) {
// 		return partidoRepository.getPartidosByCreatorId(id);
// 	}

// 	public Boolean getJugadorDentroPartido(Integer partidoId, Principal principal){
// 		Partido p = partidoRepository.findById(partidoId).get();
// 		Boolean estaDentro = false;
// 		List<Jugador> lista = p.getJugadores();
// 		for (Jugador jug:lista){
// 			if(jug.getUser().getUsername().equals(principal.getName())){
// 				estaDentro = true;
// 			}
// 		}
// 		return estaDentro;
// 	}


// 	public Boolean getJugadorEnEsperaPartido(Integer partidoId, Principal principal){
// 		Set<Solicitud> lista = solicitudRepository.findSolicitudesByPartidoId(partidoId);
// 		Boolean estaEnEspera = false;
// 		for (Solicitud solicitud:lista){
// 			if(solicitud.getJugador().getUser().getUsername().equals(principal.getName())){
// 				estaEnEspera = true;
// 			}
// 		}
// 		return estaEnEspera;
// 	}

// 	// Filtrar partidos
// 	public Page<Partido> filtrarPartidos(Sexo sexo, Tipo tipoPartido, int page) {
		
// 		Pageable pageable = PageRequest.of(page,tamanoPaginacionPorPagina);
// 		Page<Partido> partidosSinFiltrar = partidoPageRepository.findAll(pageable);
		
//         if (sexo != null && tipoPartido != null) {
//             Page<Partido> partidosPorSexoYTipo = partidoPageRepository.findBySexoAndTipo(pageable, sexo, tipoPartido);
// 			return partidosPorSexoYTipo;
//         } else if (sexo == null && tipoPartido != null) {
// 			Page<Partido> partidosPorTipo = partidoPageRepository.findByTipo(pageable, tipoPartido);
// 			return partidosPorTipo;
// 		} else if (sexo != null && tipoPartido == null) {
// 			Page<Partido> partidosPorSexo = partidoPageRepository.findBySexo(pageable, sexo);
// 			return partidosPorSexo;
//         } else {
// 			return partidosSinFiltrar;
// 		}
        
//     }

// 	public Set<String> getCiudades() {
// 		List<Partido> partidos = partidoRepository.findAll();
// 		Set<String> ciudades = partidos.stream().map(p -> p.getCentro().getCiudad())
// 			.map(c -> c.replace("á", "a").replace("é", "e")
// 			.replace("í", "i").replace("ó", "o")
// 			.replace("ú", "u").toUpperCase()).collect(Collectors.toSet());
// 		return ciudades;
// 	}

//   public List<Partido> getPartidosDelJugador(Integer page, Pageable pageable, Jugador jugador){
//     return partidoRepository.findAllPageable(pageable).stream()
//     						.filter(p->p.getJugadores().contains(jugador))
//     						.collect(Collectors.toList());
//   }

//   	public Page<Partido> buscarPartidosPorJugador (int page, Jugador jugador){
// 		Pageable pageable = PageRequest.of(page,tamanoPaginacionPorPagina);
// 		return partidoPageRepository.findByJugadoresId(pageable, jugador.getId());
// 	}

// 	public void salirPartido (Partido partido, Jugador jugador){
// 		Set<Partido> partidos = jugador.getPartidos();
// 		partidos.remove(partido);
// 		jugador.setPartidos(partidos);
		
// 		List<Jugador> jugadores = partido.getJugadores();
// 		jugadores.remove(jugador);
// 		partido.setJugadores(jugadores);

// 		partidoRepository.save(partido);
// 	}

// 	public boolean puedeEditarPartido(Jugador jugador, Partido partido) {
// 		return partido.getCreador().getId() == jugador.getId() && partido.getJugadores().size() == 1;
// 	}

}
