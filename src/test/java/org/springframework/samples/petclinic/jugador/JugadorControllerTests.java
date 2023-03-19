// package org.springframework.samples.petclinic.jugador;

// import static org.hamcrest.Matchers.hasProperty;
// import static org.mockito.BDDMockito.given;
// import static org.hamcrest.Matchers.is;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.context.annotation.FilterType;
// import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
// import org.springframework.samples.volleymate.jugador.Jugador;
// import org.springframework.samples.volleymate.jugador.JugadorController;
// import org.springframework.samples.volleymate.jugador.JugadorService;
// import org.springframework.boot.test.mock.mockito.MockBean;

// @WebMvcTest(controllers = JugadorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
// public class JugadorControllerTests {

//     private static final int TEST_JUGADOR_ID = 17;

//     @Autowired
//     private JugadorController jugadorController;

//     @MockBean
//     private JugadorService jugadorService;

//     @Autowired
//     private MockMvc mockMvc;

//     private Jugador carlos;

//     @BeforeEach
//     void setup() {
//         carlos = new Jugador();
//         carlos.setId(TEST_JUGADOR_ID);
//         carlos.setFirstName("Carlos");
//         carlos.setLastName("Cano");
//         carlos.setTelephone("612345789");
//         given(this.jugadorService.findJugadorById(TEST_JUGADOR_ID)).willReturn(carlos);
//     }

//     	@WithMockUser(value = "spring")
//         @Test
//         void testShowJugador() throws Exception {
//             mockMvc.perform(get("/jugadores/{jugadorId}", TEST_JUGADOR_ID)).andExpect(status().isOk())
//                     .andExpect(model().attribute("jugador", hasProperty("lastName", is("Cano"))))
// 				    .andExpect(model().attribute("jugador", hasProperty("firstName", is("Carlos"))))
// 				    .andExpect(model().attribute("jugador", hasProperty("telephone", is("612345789"))))
// 				    .andExpect(view().name("jugadores/jugadorDetails"));
//         }
    
// }
