package org.springframework.samples.volleymate.jugador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
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

    @GetMapping("/jugadores")
    public ModelAndView showJugadorAutenticado() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
                ModelAndView mav = new ModelAndView("jugadores/detallesJugadorAutenticado");
                mav.addObject(this.jugadorService.findJugadorById(jugador.getId()));
                return mav;
            
            } else {
                ModelAndView mav = new ModelAndView("welcome");
                return mav;
            }
    }

    @GetMapping("/jugadores/{jugadorId}")
    public String showJugador(@PathVariable("jugadorId") int jugadorId, Map<String,Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
                Jugador jugador2 = jugadorService.findJugadorById(jugadorId);
                if (jugador.equals(jugador2)){
                    model.put("jugador",jugador);
                    return "redirect:/jugadores";
                } else {
                    model.put("jugador",jugador2);
                    return "jugadores/detallesJugador";
                }
            } else {
                return "welcome";
            }
    }


    @GetMapping("/jugadores/mispartidos")
    public String showMisPartidos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
				Set<Partido> partidos = jugador.getPartidos();
                List<Partido> arr = new ArrayList<>();
                arr.addAll(partidos);
                Comparator<Partido> comparador = Comparator.comparing(Partido::getFechaCreacion);
                List<Partido> listaOrdenada =  arr.stream().sorted(comparador.reversed()).collect(Collectors.toList());
				model.addAttribute("partidos", listaOrdenada);
				return "jugadores/misPartidos";
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