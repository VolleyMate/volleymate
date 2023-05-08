package org.springframework.samples.petclinic.logro;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.logro.Logro;
import org.springframework.samples.volleymate.logro.LogroRepository;
import org.springframework.samples.volleymate.logro.LogroService;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
    "spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LogroControllerTests {

    @Autowired
    private static MockMvc mockMvc;

    private static LogroRepository achievementRepo;

    private static LogroService achievementService;

    //private static JugadorRepository playerRepo;

    private static JugadorService playerService;

    // ### REPOS ###

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

    // ### -------- ###

    @BeforeEach
    public void setUp() {

        achievementRepo = mock(LogroRepository.class);
        achievementService = new LogroService(achievementRepo);
    }

    @Test
    public void testGetAllAchievements() throws Exception {
        // Prepare mock data
        Jugador jugador = new Jugador();
        List<Logro> logros = new ArrayList<>();
        Logro logro = new Logro();
        logro.setMetrica("testmetric");
        logro.setThreshold(100.);
        logro.setJugadores(new ArrayList<>());
        logros.add(logro);
        when(achievementService.getAllAchievements()).thenReturn(logros);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/logro").principal(new UsernamePasswordAuthenticationToken("testuser", "password")))
                .andExpect(status().isOk())
                .andExpect(view().name("logro/listado"))
                .andExpect(model().attribute("logros", logros))
                .andExpect(model().attribute("jugador", jugador))
                .andExpect(model().attribute("jugadorAutenticado", jugador))
                .andExpect(model().attribute("esAdmin", true))
                .andExpect(model().attribute("conseguido", jugador.getLogros()))
                .andExpect(model().attribute("progreso", new HashMap<String, Double>()))
                .andReturn();

        verify(playerService, times(2)).findJugadorByUsername("testuser");
        verify(playerService, times(1)).esAdmin(jugador);
        verify(achievementService, times(1)).getAllAchievements();
    }

    @Test
    public void testSeeOtherPlayerAchievements() throws Exception {
        // Prepare mock data
        Jugador jugador = new Jugador();
        List<Logro> logros = new ArrayList<>();
        Logro logro = new Logro();
        logro.setMetrica("testmetric");
        logro.setThreshold(100.);
        logro.setJugadores(new ArrayList<>());
        logros.add(logro);
        when(playerService.findJugadorById(1)).thenReturn(jugador);
        when(achievementService.getAllAchievements()).thenReturn(logros);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/logro/player/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("logro/listado"))
                .andExpect(model().attribute("logros", logros))
                .andExpect(model().attribute("jugador", jugador))
                .andExpect(model().attribute("conseguido", jugador.getLogros()))
                .andExpect(model().attribute("progreso", new HashMap<String, Double>()))
                .andReturn();

        verify(playerService, times(1)).findJugadorById(1);
        verify(achievementService, times(1)).getAllAchievements();
    }
  }
