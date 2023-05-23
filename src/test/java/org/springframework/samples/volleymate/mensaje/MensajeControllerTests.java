package org.springframework.samples.volleymate.mensaje;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = MensajeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
    "database=hsqldb",
"spring.h2.console.enabled=true" })
public class MensajeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MensajeService mensajeService;

    @MockBean
    JugadorService jugadorService;

    @MockBean
    UserService userService;

    @MockBean
    AuthoritiesService authoritiesService;

    @MockBean
    PartidoService partidoService;

    @BeforeEach
    void setUp() {

        // ==== User1 ==== //
        User user1 = new User();
		user1.setUsername("Test1");
		user1.setPassword("123");
		user1.setEnabled(true);

        // ==== Authorities ==== //
		Authorities rol1 = new Authorities();
		rol1.setAuthority("jugador");
		user1.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol1)));

        // ==== Jugador1 ==== //
		Jugador jugador1 = new Jugador();
		jugador1.setFirstName("Jugador1");
		jugador1.setLastName("Davis");
		jugador1.setUser(user1);
		jugador1.setVolleys(200);
		jugador1.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador1);
        userService.saveUser(user1);
        authoritiesService.saveAuthorities(user1.getUsername(), "jugador");

        // ==== User2 ==== //
        User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);

        // ==== Authorities ==== //
		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
		user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));

        // ==== Jugador2 ==== //
		Jugador jugador2 = new Jugador();
		jugador2.setFirstName("Jugador2");
		jugador2.setLastName("Gomez");
		jugador2.setUser(user2);
		jugador2.setVolleys(200);
		jugador2.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador2);
        userService.saveUser(user2);
        authoritiesService.saveAuthorities(user2.getUsername(), "jugador");

        // ==== Partido 1 ==== //
        Partido partido = new Partido();
        partido.setId(1);
        partido.setNombre("Nombre prueba");
        partido.setDescripcion("Desc prueba");
        partido.setTipo(Tipo.VOLEIBOL);
        partido.setCreador(jugador1);
        partido.setNumJugadoresNecesarios(2);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador2);
        jugadores.add(jugador1);
        partido.setJugadores(jugadores);
        partidoService.save(partido);

        // === Mensaje 1 === //
        Mensaje mensaje = new Mensaje();
        mensaje.setContenidoMensaje("Este es un contenido de prueba");
        mensaje.setEmisor(jugador2);
        mensaje.setPartido(partido);
        given(this.mensajeService.findAllMensajesByPartidoId(1).size()).willReturn(1);
        given(this.mensajeService.findTusMensajes(jugador2).size()).willReturn(1);

        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(mensaje);
        partido.setMensajes(mensajes);
        partidoService.save(partido);
    }

    /*@WithMockUser(value = "spring", username = "Test1", authorities = "jugador")
    @Test
    public void testShowPartidos() throws Exception {
        mockMvc.perform(get("/chat/1")).andExpect(status().isOk())
        .andExpect(view().name("chat/chat"));
    }*/

    /*@WithMockUser(value = "spring", username = "Test1", authorities = "jugador")
    @Test
    public void testEnviarMensaje() throws Exception {
        mockMvc.perform(post("/chat/enviar/{partidoId}")).andExpect(status().isOk())
        .andExpect(view().name("chat/chat"));
    }*/


    
}
