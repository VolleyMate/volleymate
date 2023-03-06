package org.springframework.samples.petclinic.jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.jugador.exceptions.YaUnidoException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
