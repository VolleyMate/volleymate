package org.springframework.samples.petclinic.partido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.jugador.Jugador;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartidoController {

    //SERVICES
	@Autowired
	private PartidoService partidoService;
    
    //VIEWS
	private static final String VIEW_LISTA_PARTIDOS = "partidos/X";
	private static final String VIEW_PARTIDOS_CREATE_OR_UPDATE = "partidos/X";

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
	public String processCreationForm(Jugador jugador, @Valid Partido partido, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("partido", partido);
			return VIEW_PARTIDOS_CREATE_OR_UPDATE;
		}
		else {
			this.partidoService.save(partido);
			return "redirect:/partidos/"+partido.getId();
		}
	}
	
	//Update Partidos
	
	@GetMapping(value = "/partidos/{partidoId}/edit")
	public String initUpdateForm(@PathVariable("partidoId") int partidoId, ModelMap model) {
		Partido partido = this.partidoService.findPartidoById(partidoId);
		model.put("partido", partido);
		return VIEW_PARTIDOS_CREATE_OR_UPDATE;
	}
	
    @PostMapping(value = "/partidos/{partidoId}/edit")
	public String processUpdateForm(@Valid Partido partido, BindingResult result, Jugador jugador,@PathVariable("partidoId") int partidoId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("partido", partido);
			return VIEW_PARTIDOS_CREATE_OR_UPDATE;
		}
		else {
			this.partidoService.save(partido);
			return "redirect:/partidos/{partidoId}";
		}
	}
}
	
	

