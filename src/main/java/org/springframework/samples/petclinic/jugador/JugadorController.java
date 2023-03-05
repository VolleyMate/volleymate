package org.springframework.samples.petclinic.jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
