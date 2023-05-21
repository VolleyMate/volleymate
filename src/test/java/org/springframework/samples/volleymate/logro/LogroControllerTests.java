package org.springframework.samples.volleymate.logro;

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
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.volleymate.centro.CentroService;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.logro.Logro;
import org.springframework.samples.volleymate.logro.LogroController;
import org.springframework.samples.volleymate.logro.LogroRepository;
import org.springframework.samples.volleymate.logro.LogroService;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.stereotype.Service;
//import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.context.annotation.FilterType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LogroController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
    "database=hsqldb",
"spring.h2.console.enabled=true" })
/*@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
    "spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })*/
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LogroControllerTests {

    @Autowired
    private static MockMvc mockMvc;

    @MockBean
    private static LogroRepository achievementRepo;

    @MockBean
    private static LogroService achievementService;

    //private static JugadorRepository playerRepo;

    @MockBean
    private static JugadorService playerService;

    // ### REPOS ###

    @MockBean
    protected PartidoService partidoService;

    @MockBean
    protected CentroService centroService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    protected SolicitudService solicitudService;

    @MockBean
    protected UserService userService;

    @MockBean
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
