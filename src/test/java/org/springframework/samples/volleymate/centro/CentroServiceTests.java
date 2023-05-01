package org.springframework.samples.volleymate.centro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;

import org.springframework.samples.volleymate.partido.PartidoService;

import org.springframework.samples.volleymate.solicitud.SolicitudService;

import org.springframework.samples.volleymate.user.AuthoritiesService;

import org.springframework.samples.volleymate.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CentroServiceTests {
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

    private CentroRepository centroRepository;

    @BeforeEach
    public void setUp(){
        centroRepository = mock(CentroRepository.class);
        centroService = new CentroService(centroRepository);
    }

    @Test
    public void testValidarCentroEmptyFields() {
        Jugador jugador = new Jugador();
        Centro centro = new Centro(jugador, "", "", "", "", true);
        List<String> errores = centroService.validarCentro(centro);

        assertEquals(6, errores.size());
        assertTrue(errores.contains("El nombre no puede estar vacío"));
        assertTrue(errores.contains("La dirección no puede estar vacía"));
        assertTrue(errores.contains("La ciudad no puede estar vacía"));
        assertTrue(errores.contains("La dirección en maps no puede estar vacía"));
        assertTrue(errores.contains("La url no es válida"));
        assertTrue(errores.contains("La dirección en maps no es válida, usa un link de google maps"));
    }

    @Test
    public void testValidarCentroInvalidMapsUrl() {
        Jugador jugador = new Jugador();
        Centro centro = new Centro(jugador, "Nombre", "Direccion", "Ciudad", "http:/invalidurl.com", true);
        List<String> errores = centroService.validarCentro(centro);

        assertEquals(2, errores.size());
        assertTrue(errores.contains("La url no es válida"));
        assertTrue(errores.contains("La dirección en maps no es válida, usa un link de google maps"));
    }

    @Test
    public void testValidarCentroInvalidMapsUrlWithoutMaps() {
        Jugador jugador = new Jugador();
        Centro centro = new Centro(jugador, "Nombre", "Direccion", "Ciudad", "https://example.com/withoutm@ps", true);
        List<String> errores = centroService.validarCentro(centro);

        assertEquals(1, errores.size());
        assertTrue(errores.contains("La dirección en maps no es válida, usa un link de google maps"));
    }

    @Test
    public void testValidarCentroValid() {
        Jugador jugador = new Jugador();
        Centro centro = new Centro(jugador, "Nombre", "Direccion", "Ciudad", "https://maps.google.com/example", true);
        List<String> errores = centroService.validarCentro(centro);

        assertEquals(0, errores.size());
    }
}
