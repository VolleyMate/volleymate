package org.springframework.samples.volleymate.partido;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.centro.CentroService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;

@Controller
public class PartidoController {

    //SERVICES
	@Autowired
	private PartidoService partidoService;
	@Autowired
	private JugadorService jugadorService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private CentroService centroService;

    //VIEWS
	private static final String VIEW_LISTA_PARTIDOS = "partidos/listaPartidos";
	private static final String VIEW_PARTIDOS_CREATE_OR_UPDATE = "partidos/crearPartido";
	private static final String VIEW_SOLICITUDES_PARTIDO = "partidos/solicitudes";

	@GetMapping("/partidos")
	public String filtrarPartidos(@RequestParam(required = false) Sexo sexo, 
								@RequestParam(required = false) Tipo tipo,
								Map<String,Object> model,
								@RequestParam(defaultValue="0") int page) {
									
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
    		Page<Partido> partidos = partidoService.filtrarPartidos(sexo,tipo,page);
			Integer numPartidos = partidos.getNumberOfElements();
    		model.put("partidos", partidos);
			model.put("numPartidos", numPartidos);
    		return VIEW_LISTA_PARTIDOS;
		}else{
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
		mav.addObject("puedeEditar", partidoService.puedeEditarPartido(jugadorService.findJugadorByUsername(principal.getName()),partidoService.findPartidoById(partidoId)));
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
		Boolean puedeCrear = jugador.getVolleys()>=150;
		model.put("puedeCrear", puedeCrear);
		model.put("partido", partido);
		model.put("centros", centroService.findAcceptedCentros());
		return VIEW_PARTIDOS_CREATE_OR_UPDATE;
	}

	@PostMapping(value = "/partidos/new")
	public String processCreationForm(@Valid Partido partido, BindingResult result, ModelMap model,Principal principal) throws YaUnidoException {
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
		if(partido.getNumJugadoresNecesarios()==null || partido.getNumJugadoresNecesarios()<=1){
			errores.add("El número de jugadores debe ser mayor que 1");
		}
		Jugador jugador = jugadorService.findJugadorByUsername(principal.getName());
		if (!errores.isEmpty()) {
			model.put("partido", partido);
			model.put("errors", errores);
			model.put("centros", centroService.findAcceptedCentros());
			Boolean puedeCrear = jugador.getVolleys()>=150;
			model.put("puedeCrear", puedeCrear);
			return VIEW_PARTIDOS_CREATE_OR_UPDATE;
		} else {
			Jugador jugadorCreador = jugadorService.findJugadorById(partido.getCreador().getId());
			jugadorCreador.setVolleys(jugadorCreador.getVolleys()-150);
			this.partidoService.save(partido);
			jugadorService.unirsePartida(partido.getCreador().getId(), partido.getId());
			return "redirect:/partidos/"+partido.getId();
		}
	}

	// Show solicitudes

	@GetMapping(value = "/partidos/solicitudes/{partidoId}")
	public String showSolicitudesPartido(@PathVariable("partidoId") int partidoId, ModelMap model) {
		Set<Solicitud> solic = solicitudService.findAllSolicitudesByPartidoId(partidoId);
		model.put("solicitudes", solic);
		return VIEW_SOLICITUDES_PARTIDO;
	}

	@GetMapping(value = "/jugadores/partido/{partidoId}")
	public ModelAndView showJugadoresPartido(@PathVariable("partidoId") int partidoId, ModelMap model) {
		Partido partido = partidoService.findPartidoById(partidoId);
		List<Jugador> jugadores= partido.getJugadores();
		ModelAndView mav = new ModelAndView("partidos/jugadoresPartido");
        mav.addObject("jugadores", jugadores);
		return mav;
	}

	@GetMapping(value = "/jugadores/volleys/añadir/{jugadorId}")
	public String añadirVolleyString(@PathVariable("jugadorId") int jugadorId, ModelMap model, Principal principal) {
		Jugador jugadorAut = jugadorService.findJugadorByUsername(principal.getName());
		Boolean esAdmin = jugadorService.esAdmin(jugadorAut);
		if(esAdmin){
			Jugador jugador = jugadorService.findJugadorById(jugadorId);
			jugador.setVolleys(400);
			jugadorService.saveJugador(jugador);
			return "redirect:/jugadores/"+jugador.getId();	
		} else {
			return "redirect:/";
		}
	}

	@GetMapping(value = "/partidos/edit/{id}")
	public String initEditForm(Map<String, Object> model, @PathVariable("id") int id, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Partido partido = partidoService.findPartidoById(id);
			Jugador player = jugadorService.findJugadorByUsername(principal.getName());
			if (partidoService.puedeEditarPartido(player, partido)){
				model.put("partido", partido);
				model.put("centros", centroService.findAcceptedCentros());
				return "partidos/editarPartido";
			}else{
				return "welcome";
			}
		}else{
			return "welcome";
		}
	}
	
	
	@PostMapping(value = "/partidos/edit/{id}")
	public String processEditForm(@Valid Partido partido, BindingResult result, @PathVariable("id") int id, Map<String, Object> model){

		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return "partidos/editarPartido";
		}
		else {
			Partido partidoUpdate = this.partidoService.findPartidoById(partido.getId());
			BeanUtils.copyProperties(partido,partidoUpdate,"mensajes","jugadores","solicitudes","creador","fechaCreacion","precioPersona"); 
			this.partidoService.save(partidoUpdate);
			return "redirect:/partidos/" + partidoUpdate.getId();
		}						
		
	}


	// Salirse de un partido
	@GetMapping(value = "/partidos/salir/{partidoId}")
	public String salirPartido(@PathVariable("partidoId") int partidoId, ModelMap model, Principal principal) throws YaUnidoException {
		if(partidoService.getJugadorDentroPartido(partidoId, principal)) {
			Jugador jugador = jugadorService.findJugadorByUsername(principal.getName());
			Partido partido = partidoService.findPartidoById(partidoId);
			partidoService.salirPartido(partido,jugador);
			return "redirect:/partidos";
		}else{
			return "redirect:/";
		}
	}

	//Eliminar Partido
	@GetMapping(value = "/partidos/eliminar/{partidoId}")
	public String eliminarPartido(@PathVariable("partidoId") int partidoId, ModelMap model, Principal principal) {
		Partido partido = partidoService.findPartidoById(partidoId);
		Jugador jugadorAut = jugadorService.findJugadorByUsername(principal.getName());
		Boolean esAdmin = jugadorService.esAdmin(jugadorAut);
		if(esAdmin){
			partidoService.deletePartido(partido);
			return "redirect:/partidos";
		} else {
			return "redirect:/";
		}
	}

}
	
	

