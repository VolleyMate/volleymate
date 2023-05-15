package org.springframework.samples.volleymate.centro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.volleymate.aspecto.Aspecto;
import org.springframework.samples.volleymate.aspecto.AspectoService;
import org.springframework.samples.volleymate.configuration.SecurityConfiguration;
import org.springframework.samples.volleymate.jugador.Ciudad;
import org.springframework.samples.volleymate.jugador.EmailService;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.logro.Logro;
import org.springframework.samples.volleymate.partido.Partido;
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
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@WebMvcTest(controllers = CentroController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class, properties = {
    "database=hsqldb",
"spring.h2.console.enabled=true" })
class CentroControllerTests {

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

    @MockBean
    private CentroService centroService;
    
    @MockBean
    private EmailService emailService;


    @BeforeEach
	void setup() throws YaUnidoException {

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

        // ==== Centro ==== //
		Centro centro = new Centro();
        centro.setNombre("Centro 1");
        centro.setCiudad("Sevilla");
        centro.setDireccion("Calle 1");
        centro.setMaps("maps");
        centro.setEstado(true);
        centro.setCreador(george);
        centro.setPartidos(new ArrayList<Partido>());


        
	}


    // @GetMapping(value = "/centros/solicitud/new")
    // public String initCreationForm(Map<String, Object> model) {
    //     Centro centro = new Centro();
    //     centro.setEstado(false);
    //     model.put("centro", centro);
    //     return VISTA_CREAR_CENTROS;
    // }

    @WithMockUser(value = "spring", username = "Test", authorities = "admin")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/centros/solicitud/new")).andExpect(status().isOk())
				.andExpect(view().name("centros/crearCentros"));
	}

    // @WithMockUser(value = "spring", username = "Test", authorities = "admin")
    // @Test
	// void testProcessCreationForm() throws Exception {
	// 	mockMvc.perform(post("/centros/solicitud/new")
    //     .param("nombre", "Centro 2")
	// 	.param("ciudad", "SEVILLA")
	// 	.param("direccion", "JDNSFMBDNSF")
	// 	.param("maps", "https://github.com/VolleyMate/volleymate/tree/TASK-134"))
    //     .with(csrf()).andExpect(status().isOk());
	// }

    @WithMockUser(value = "spring", username = "Test", authorities = "admin")
    @Test
	void testShowCentros() throws Exception {
		mockMvc.perform(get("/centros")).andExpect(status().isOk())
				.andExpect(view().name("centros/listaCentros"));
	}

    /*
    @GetMapping(value = "/centros")
    public String showCentros(Map<String, Object> model, @RequestParam(defaultValue="0") int page) {
        Page<Centro> centros = centroService.findAcceptedCentrosPageable(page);
        Integer numCentros = centros.getNumberOfElements();
        List<Centro> centrosSol = centroService.getSolicitudesCentros();
        model.put("centrosSol", centrosSol);
        model.put("centros", centros);
        model.put("numCentros", numCentros);
        return VISTA_LISTAR_CENTROS;
    }

    @GetMapping("/centros/{centroId}")
    public ModelAndView showCentroDetails(@PathVariable("centroId") int centroId, Principal principal) {
		ModelAndView mav = new ModelAndView("centros/centroDetails");
		mav.addObject("centro", this.centroService.findCentroById(centroId));
		mav.addObject("jugadorLogueado", this.jugadorService.findJugadorByUsername(principal.getName()));
		return mav;
    }

    @GetMapping(value = "/centros/solicitud/list")
    public String showSolicitudes(Map<String, Object> model) {
        List<Centro> centros = centroService.getSolicitudesCentros();
        
        model.put("centros", centros);
        return "centros/solicitudesList";
    }

    @GetMapping(value = "/centros/solicitud/accept/{centroId}")
    public String acceptSolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede aceptar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            centro.setEstado(true);
            centroService.saveCentro(centro);
            this.emailService.aceptarSolicitudEmail(centro.getCreador().getUser().getCorreo());
            return "centros/accept";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/centros/solicitud/deny/{centroId}")
    public String denySolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede denegar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            this.emailService.denegarSolicitudEmail(centro.getCreador().getUser().getCorreo());
            centro.setCreador(null);
            centroService.saveCentro(centro);
            centroService.deleteCentro(centro);
            return "centros/deny";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    //Eliminar centro para administrador
    @GetMapping(value = "/centros/delete/{centroId}")
    public String deleteCentro(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede eliminar el centro si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            centroService.deleteCentro(centro);
            return "centros/delete";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/centros/edit/{centroId}")
	public String initEditForm(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
			Centro centro = centroService.findCentroById(centroId);
			model.put("centro", centro);
			return VISTA_EDITAR_CENTROS;
			
		}else{
			return "welcome";
		}
	}
	
	
	@PostMapping(value = "/centros/edit/{centroId}")
	public String processEditForm(@Valid Centro centro, BindingResult result, @PathVariable("centroId") int centroId, Map<String, Object> model){
        List<String> errores = centroService.validarCentro(centro);
		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VISTA_EDITAR_CENTROS;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_EDITAR_CENTROS;
        } else {            Centro centroUpdate = this.centroService.findCentroById(centroId);
			BeanUtils.copyProperties(centro,centroUpdate,"nombre","ciudad","direccion","maps","estado"); 
			this.centroService.saveCentro(centro);
			return "redirect:/centros/";
		}						
		
	}
 * 
 */
}
