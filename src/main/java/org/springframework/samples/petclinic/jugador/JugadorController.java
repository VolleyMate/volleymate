package org.springframework.samples.petclinic.jugador;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.partido.Partido;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JugadorController {
    
    private final JugadorService jugadorService;

    @Autowired
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping("/jugadores/{jugadorId}")
    public ModelAndView showJugador(@PathVariable("jugadorId") int jugadorId) {
        ModelAndView mav = new ModelAndView("jugadores/jugadorDetails");
        mav.addObject(this.jugadorService.findJugadorById(jugadorId));
        return mav;
    }

    @GetMapping("/jugador/mispartidos")
    public String showMisPartidos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
				Set<Partido> partidos = jugador.getPartidos();
				model.addAttribute("partidos", partidos);
				return "jugador/misPartidos";
			}
			return "redirect:/";
		}
		return "redirect:/";
    }
}
