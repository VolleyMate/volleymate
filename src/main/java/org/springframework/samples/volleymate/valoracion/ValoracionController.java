package org.springframework.samples.volleymate.valoracion;

import java.security.Principal;

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
		}
		else {
			this.vService.saveValoracion(valoracion);
			return "redirect:/jugadores/" + valoracion.getRatedPlayer().getId();
		}
	}
    
}
