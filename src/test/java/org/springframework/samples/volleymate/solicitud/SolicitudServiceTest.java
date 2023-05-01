package org.springframework.samples.volleymate.solicitud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.centro.Centro;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.partido.Tipo;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SolicitudServiceTest {
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

    private SolicitudRepository solicitudRepository;

    @BeforeEach
    public void setUp() {
        solicitudRepository = mock(SolicitudRepository.class);
        solicitudService = new SolicitudService(solicitudRepository);
    }
    
    @Test
    public void shouldHaveSolicitud() {
        // Creamos una solicitud para el jugador y comprobamos si devuelve TRUE el
        // método del servicio.
        Integer partidoId = 1;
        Principal principal = () -> "username";
        Set<Solicitud> solicitudes = new HashSet<>();
        Solicitud solicitud = new Solicitud();
        Jugador jugador = new Jugador();
        User user = new User();
        user.setUsername(principal.getName());
        jugador.setUser(user);
        solicitud.setJugador(jugador);
        solicitud.setPartido(null);
        solicitudes.add(solicitud);
        when(solicitudRepository.findSolicitudesByPartidoId(partidoId)).thenReturn(solicitudes);

        // Call the method under test
        Boolean result = solicitudService.getYaTieneSolicitud(partidoId, principal);

        // Assert the expected result
        assertTrue(result);
        verify(solicitudRepository).findSolicitudesByPartidoId(partidoId);
    }

    @Test
    public void testGetYaTieneSolicitudNegative() {
        // Prepare test data and mock behavior
        Integer partidoId = 1;
        Principal principal = () -> "username";
        Set<Solicitud> solicitudes = new HashSet<>();
        Solicitud solicitud = new Solicitud();
        Jugador jugador = new Jugador();
        User user = new User();
        user.setUsername("differentUsername");
        jugador.setUser(user);
        solicitud.setJugador(jugador);
        solicitudes.add(solicitud);
        when(solicitudRepository.findSolicitudesByPartidoId(partidoId)).thenReturn(solicitudes);

        // Call the method under test
        Boolean result = solicitudService.getYaTieneSolicitud(partidoId, principal);

        // Assert the expected result
        assertFalse(result);
        verify(solicitudRepository).findSolicitudesByPartidoId(partidoId);
    }
}