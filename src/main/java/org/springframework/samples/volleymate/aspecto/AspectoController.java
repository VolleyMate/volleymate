package org.springframework.samples.volleymate.aspecto;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stripe.model.PaymentLink.AfterCompletion.Redirect;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




@Controller
public class AspectoController {

    public final AspectoService aspectoService;


    private static final String VISTA_CREAR_ASPECTO = "jugadores/crearAspecto";
    private static final String VISTA_EDITAR_ASPECTOS = "jugadores/editarAspecto";


    @Autowired
    public AspectoController(AspectoService aspectoService) {
        this.aspectoService = aspectoService;
    }

    @GetMapping(value = "/tienda/aspectos/nuevo")
    public String initCreationForm(Map<String, Object> model) {
        Aspecto aspecto = new Aspecto();
        model.put("aspecto", aspecto);
        return VISTA_CREAR_ASPECTO;
    }

    @PostMapping(value = "/tienda/aspectos/nuevo")
    public String processCreationForm(@Valid Aspecto aspecto, BindingResult result, Map<String, Object> model, Principal principal, RedirectAttributes redirAttrs) {
        Aspecto aspc = aspecto;
        List<String> errores = aspectoService.validarAspecto(aspc);
        if(result.hasErrors()) {
            model.put("errors", result.getAllErrors());
            return VISTA_CREAR_ASPECTO;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_CREAR_ASPECTO;
        } else{
            this.aspectoService.save(aspc);
            redirAttrs.addFlashAttribute("mensajeExito", "Aspecto creado correctamente");
            return "redirect:/tienda/";
        }
    }

    @GetMapping(value = "/tienda/aspectos/edit/{aspectoId}")
	public String initEditForm(Map<String, Object> model, @PathVariable("aspectoId") int aspectoId, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
			Aspecto aspecto = aspectoService.findById(aspectoId);
			model.put("aspecto", aspecto);
			return VISTA_EDITAR_ASPECTOS;
			
		}else{
			return "welcome";
		}
	}
	
	
	@PostMapping(value = "/tienda/aspectos/edit/{aspectoId}")
	public String processEditForm(@Valid Aspecto aspecto, BindingResult result, @PathVariable("aspectoId") int aspectoId, Map<String, Object> model, RedirectAttributes redirAttrs){
        Aspecto aspc = aspecto;
        List<String> errores = aspectoService.validarAspecto(aspc);
		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VISTA_EDITAR_ASPECTOS;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_EDITAR_ASPECTOS;
        } else {            Aspecto aspectoUpdate = this.aspectoService.findById(aspectoId);
			BeanUtils.copyProperties(aspc,aspectoUpdate,"Imagen","Precio"); 
			this.aspectoService.saveAspecto(aspecto);
            redirAttrs.addFlashAttribute("mensajeExito", "Aspecto editado correctamente");
			return "redirect:/tienda/";
		}						
		
	}

    //Eliminar aspecto para administrador
    @GetMapping(value = "/tienda/aspectos/delete/{aspectoId}")
    public String deleteAspecto(@PathVariable("aspectoId") int aspectoId, Map<String, Object> model, RedirectAttributes redirAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            Aspecto aspecto = aspectoService.findById(aspectoId);
            List<Jugador> jugadores = aspecto.getJugadores();
            for(Jugador jugador: jugadores){
                jugador.setImage("/resources/images/perfilPorDefecto.png");
            }
            aspectoService.deleteAspecto(aspecto);
            redirAttrs.addFlashAttribute("mensajeExito", "Aspecto eliminado correctamente");
            return "redirect:/tienda/";
        }else{
            return "welcome";
        }
    }
}