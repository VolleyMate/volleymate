package org.springframework.samples.volleymate.partido;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartidoController {

    //SERVICES
	@Autowired
	private PartidoService partidoService;
	@Autowired
	private JugadorService jugadorService;
	@Autowired
	private SolicitudService solicitudService;
    
    //VIEWS
	private static final String VIEW_LISTA_PARTIDOS = "partidos/listaPartidos";
	private static final String VIEW_LISTA_PARTIDOS_FILTRADOS = "partidos/listaPartidosFiltrados";
	private static final String VIEW_PARTIDOS_CREATE_OR_UPDATE = "partidos/crearPartido";
	private static final String VIEW_SOLICITUDES_PARTIDO = "partidos/{partidoId}/solicitudes";

    @GetMapping(value = { "/partidos" })
	public String showPartidos(Map<String, Object> model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null){
			List<Partido> partidos = partidoService.findAllPartidos();
			Sexo[] sexos = Sexo.values();
			Tipo[] tipos = Tipo.values();
			Set<String> ciudades = partidoService.getCiudades();
			model.put("partidos", partidos);
			model.put("sexos", sexos);
			model.put("tipos", tipos);
			model.put("ciudades", ciudades);
			return VIEW_LISTA_PARTIDOS;
		} else {
			return "redirect:/";
		}
	}
    
	@GetMapping("/partidos/{partidoId}")
    public ModelAndView showPartido(@PathVariable("partidoId") int partidoId, Principal principal) {
        Boolean estaDentro = partidoService.getJugadorDentroPartido(partidoId, principal);
		Boolean estaEnEspera = partidoService.getJugadorEnEsperaPartido(partidoId, principal);
		ModelAndView mav = new ModelAndView("partidos/partidoDetails");
		mav.addObject("partido", this.partidoService.findPartidoById(partidoId));
		mav.addObject("jugadorLogueado", this.jugadorService.findJugadorByUsername(principal.getName()));
        mav.addObject("estaDentro",estaDentro);
		mav.addObject("estaEnEspera", estaEnEspera);
		return mav;
    }

    //Create Partidos
    
	@GetMapping(value = "/partidos/new")
	public String initCreationForm(Principal principal, ModelMap model) {
		Partido partido = new Partido();
		Jugador jugador = jugadorService.findJugadorByUsername(principal.getName());
		partido.setCreador(jugador);
		partido.setFechaCreacion(LocalDateTime.now());
		partido.setFecha(LocalDateTime.now());
		partido.setNumJugadoresNecesarios(1);
		model.put("partido", partido);
		return VIEW_PARTIDOS_CREATE_OR_UPDATE;
	}

	@PostMapping(value = "/partidos/new")
	public String processCreationForm(@Valid Partido partido, BindingResult result, ModelMap model) throws YaUnidoException {
		List<String> errores = new ArrayList<>();
		if(partido.getFecha().isBefore(LocalDateTime.now())) {
			errores.add("La fecha no puede ser previa al día de hoy");
		}
		if(partido.getDescripcion()==null || partido.getDescripcion()=="") {
			errores.add("La descripción no puede estar vacía");
		}
		if(partido.getNombre()==null || partido.getNombre()=="") {
			errores.add("El nombre no puede estar vacío");
		}
		if(partido.getCiudad()== null || partido.getCiudad()=="") {
			errores.add("La ciudad del partido no puede estar vacía");
		}
		if(partido.getDireccion()== null || partido.getDireccion()=="") {
			errores.add("La dirección del partido no puede estar vacía");
		}
		if(partido.getNumJugadoresNecesarios()==null || partido.getNumJugadoresNecesarios()<=1){
			errores.add("El número de jugadores debe ser mayor que 1");
		}
		if (!errores.isEmpty()) {
			model.put("partido", partido);
			model.put("errors", errores);
			return VIEW_PARTIDOS_CREATE_OR_UPDATE;
		} else {
			this.partidoService.save(partido);
			jugadorService.unirsePartida(partido.getCreador().getId(), partido.getId());
			return "redirect:/partidos/"+partido.getId();
		}
	}

	// Show solicitudes

	@GetMapping(value = "partidos/{partidoId}/solicitudes")
	public String showSolicitudesPartido(@PathVariable("partidoId") int partidoId, ModelMap model) {
		Set<Solicitud> conj = new HashSet<>();
		Set<Solicitud> solic = solicitudService.findAllSolicitudesByPartidoId(partidoId);
		conj.addAll(solic);
		model.put("solicitudes", conj);
		return VIEW_SOLICITUDES_PARTIDO;
	}

  	@GetMapping(value = "/partidos?tipo={tipoP}&sexo={sexoP}&ciudad={ciudadP}")
	public String showPartidosFiltrados(Map<String, Object> model, @PathVariable("tipoP") String tipoP, @PathVariable("sexoP") String sexoP, @PathVariable("ciudadP") String ciudadP) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null){
			Set<Partido> conj = new HashSet<>();
			conj.addAll(partidoService.findAllPartidos());
			if(tipoP != null && tipoP != "") {
				Set<Partido> partidosTipo = partidoService.getPartidosByTipoString(tipoP);
				conj.retainAll(partidosTipo);
			}
			if(sexoP != null && sexoP != "") {
				Set<Partido> partidosSexo = partidoService.getPartidosBySexoString(sexoP);
				conj.retainAll(partidosSexo);
			}
			if(ciudadP != null && ciudadP != "") {
				Set<Partido> partidosCiudad = partidoService.getPartidosByCiudad(ciudadP);
				conj.retainAll(partidosCiudad);
			}
			model.put("partidosFiltrados", conj);
			return VIEW_LISTA_PARTIDOS_FILTRADOS;
		} else {
			return "redirect:/";
		}
	}

}
	
	

