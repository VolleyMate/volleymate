package org.springframework.samples.volleymate.valoracion;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ValoracionController {

    //Servicios
    private ValoracionService vService;
    private JugadorService jugadorService;

    @Autowired
    public ValoracionController(JugadorService jugadorService, ValoracionService vService) {
		this.jugadorService = jugadorService;
        this.vService = vService;
    }

    //Vistas
    private static final String VIEW_VALORACION_CREATE_OR_UPDATE = "valoraciones/crearValoracion";
    
    //Crear valoracion
	@GetMapping(value = "/valoraciones/new/{jugadorId}")
	public String initCreationForm(Principal principal, ModelMap model, @PathVariable("jugadorId") int jugadorId) {
		
		Valoracion valoracion = new Valoracion();
		Jugador jugadorValorador = jugadorService.findJugadorByUsername(principal.getName());
		Jugador jugadorValorado = jugadorService.findJugadorById(jugadorId);
		
		if(jugadorValorador.equals(jugadorValorado)){
			model.put("message", "No puedes valorarte a ti mismo");
			return "welcome";
		} else if (this.vService.valoracionExiste(jugadorValorado.getId(),jugadorValorador.getId())){
			model.put("message", "Ya has valorado a este jugador");
			return "welcome";
		} else {
			valoracion.setRatedPlayer(jugadorValorado);
			valoracion.setRatingPlayer(jugadorValorador);
			model.put("valoracion", valoracion);
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		}
	}

	@PostMapping(value = "/valoraciones/new/{jugadorId}")
	public String processCreationForm(@Valid Valoracion valoracion, BindingResult result, ModelMap model, RedirectAttributes redirAttrs) {
		if (result.hasErrors()) {
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		} else {
			this.vService.saveValoracion(valoracion);
			redirAttrs.addFlashAttribute("mensajeExitoso", "Valoración enviada correctamente");
			return "redirect:/jugadores/" + valoracion.getRatedPlayer().getId();
		}
	}

	//Ver valoraciones de un jugador
	@GetMapping(value = "/valoraciones/{jugadorId}")
	public String showValoracionesJugador(ModelMap model, @PathVariable("jugadorId") int jugadorId) {
		List<Valoracion> valoraciones = this.vService.findValoracionesByJugadorId(jugadorId);
		model.put("valoraciones", valoraciones);
		return "valoraciones/valoracionesJugador";
	}
    
}
