package org.springframework.samples.petclinic.jugador;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.jugador.exceptions.YaUnidoException;
import org.springframework.samples.petclinic.partido.Partido;
import org.springframework.samples.petclinic.partido.PartidoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JugadorController {
    
    private final JugadorService jugadorService;
    
    private final PartidoService partidoService;

    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService) {
        this.jugadorService = jugadorService;
        
        this.partidoService = partidoService;
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
    
    @GetMapping("/jugador/mispartidoscreados")
    public String listMisPartidosCreados(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || !auth.isAuthenticated()){
				return "redirect:/";
			}else {
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
				Set<Partido> partidos = partidoService.getPartidosByCreatorId(jugador.getId());
				model.addAttribute("partidosCreados", partidos);
				return "jugador/misPartidosCreados";
			}
    }

    @GetMapping("/jugadores/{jugadorId}/unirse/{partidoId}")
    public String unirsePartida(@PathVariable("jugadorId") int jugadorId, @PathVariable("partidoId") int partidoId,
        RedirectAttributes redirAttrs) {
            try{
                this.jugadorService.unirsePartida(jugadorId, partidoId);
                redirAttrs.addFlashAttribute("mensajeExitoso", "Enhorabuena, ya estás dentro del partido!");
                /*
                    Aqui que el de frontend que redirija donde se tenga que redirigir, provisionalmente redirige a partidos.
                    ACORDARSE: Hay que mostrar el mensaje en la vista
                */
                String redirect = "redirect:/partidos";
                return redirect;
            }catch(YaUnidoException ex){
                redirAttrs.addFlashAttribute("mensajeYaEnPartido", "Ya estás unid@ a este partido");
                /*
                    Aqui que el de frontend que redirija donde se tenga que redirigir, provisionalmente redirige a partidos.
                    ACORDARSE: Hay que mostrar el mensaje en la vista
                */
                return "redirect:/partidos";
            }
    }
}
