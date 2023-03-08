package org.springframework.samples.petclinic.partido;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.jugador.Jugador;
import org.springframework.samples.petclinic.jugador.JugadorService;
import org.springframework.samples.petclinic.solicitud.Solicitud;
import org.springframework.samples.petclinic.solicitud.SolicitudService;
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
	private static final String VIEW_PARTIDOS_CREATE_OR_UPDATE = "partidos/createOrUpdatePartidoForm";
	private static final String VIEW_SOLICITUDES_PARTIDO = "partidos/{partidoId}/solicitudes";

    @GetMapping(value = { "/partidos" })
	public String showPartidos(Map<String, Object> model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null){
			List<Partido> partidos = partidoService.findAllPartidos();
			model.put("partidos", partidos);
			return VIEW_LISTA_PARTIDOS;
		} else {
			return "redirect:/";
		}
	}
    

	@GetMapping("/partidos/{partidoId}")
    public ModelAndView showPartido(@PathVariable("partidoId") int partidoId) {
        ModelAndView mav = new ModelAndView("partidos/partidoDetails");
        mav.addObject(this.partidoService.findPartidoById(partidoId));
        return mav;
    }

    //Create Partidos
    
	@GetMapping(value = "/partidos/new")
	public String initCreationForm(Jugador jugador, ModelMap model) {
		Partido partido = new Partido();
		partido.setCreador(jugador);
		partido.setFechaCreacion(LocalDateTime.now());
		model.put("partido", partido);
		return VIEW_PARTIDOS_CREATE_OR_UPDATE;
	}

	@PostMapping(value = "/partidos/new")
	public String processCreationForm(@Valid Partido partido, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("partido", partido);
			return VIEW_PARTIDOS_CREATE_OR_UPDATE;
		}
		else {
			this.partidoService.save(partido);
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
}
	
	

