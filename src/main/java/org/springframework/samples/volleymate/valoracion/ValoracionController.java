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
	@GetMapping(value = "/valoraciones/{jugadorId}/new")
	public String initCreationForm(Principal principal, ModelMap model, @PathVariable("jugadorId") int jugadorId) {
        Valoracion valoracion = new Valoracion();
		Jugador jugadorValorador = jugadorService.findJugadorByUsername(principal.getName());
		Jugador jugadorValorado = jugadorService.findJugadorById(jugadorId);
        valoracion.setRatedPlayer(jugadorValorado);
        valoracion.setRatingPlayer(jugadorValorador);
		model.put("valoracion", valoracion);
		return VIEW_VALORACION_CREATE_OR_UPDATE;
	}

	@PostMapping(value = "/valoraciones/{jugadorId}/new")
	public String processCreationForm(@Valid Valoracion valoracion, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		}else if (valoracion.getRatingPlayer().equals(valoracion.getRatedPlayer())) {
			model.put("message", "No puedes valorarte a ti mismo");
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		}else if(this.vService.valoracionExiste(valoracion.getRatingPlayer().getId(), valoracion.getRatedPlayer().getId())) {
			model.put("message", "Ya has valorado a este jugador");
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		}else if(valoracion.getPuntuacion() < 1 || valoracion.getPuntuacion() > 5) {
			model.put("message", "La valoración debe estar entre 1 y 5");
			return VIEW_VALORACION_CREATE_OR_UPDATE;
		}
		else {
			this.vService.saveValoracion(valoracion);
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
