package org.springframework.samples.volleymate.aspecto;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.EmailService;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.user.UserService;




@Controller
public class AspectoController {

    public final AspectoService aspectoService;


    private static final String VISTA_CREAR_ASPECTO = "jugadores/crearAspecto";
    private static final String VISTA_TIENDA_ASPECTO = "jugadores/tiendaAspectos";


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
    public String processCreationForm(@Valid Aspecto aspecto, BindingResult result, Map<String, Object> model, Principal principal) {
        List<String> errores = aspectoService.validarAspecto(aspecto);
        if(result.hasErrors()) {
            model.put("errors", result.getAllErrors());
            return VISTA_CREAR_ASPECTO;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_CREAR_ASPECTO;
        } else{
            this.aspectoService.save(aspecto);
            return VISTA_TIENDA_ASPECTO;  
        }
    }
}