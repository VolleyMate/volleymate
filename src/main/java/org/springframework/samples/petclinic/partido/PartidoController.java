package org.springframework.samples.petclinic.partido;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartidoController {

    //SERVICES
	@Autowired
	private PartidoService partidoService;
    
    //VIEWS
	private static final String VIEW_LISTA_PARTIDOS = "partidos/listaPartidos";

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

}