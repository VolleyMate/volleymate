package org.springframework.samples.volleymate.centro;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.EmailService;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.user.UserService;

@Controller
public class CentroController {
    public final CentroService centroService;
    public final JugadorService jugadorService;
    public final EmailService emailService;
    public final UserService userService;

    private static final String VISTA_LISTAR_CENTROS = "centros/listaCentros";
    private static final String VISTA_CREAR_CENTROS = "centros/crearCentros";
    private static final String VISTA_SOLICITUD_CENTRO = "centros/mensaje";
    private static final String VISTA_EDITAR_CENTROS = "centros/editarCentro";

    @Autowired
    public CentroController(CentroService centroService, JugadorService jugadorService, EmailService emailService, UserService userService) {
        this.centroService = centroService;
        this.jugadorService = jugadorService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping(value = "/centros/solicitud/new")
    public String initCreationForm(Map<String, Object> model) {
        Centro centro = new Centro();
        centro.setEstado(false);
        model.put("centro", centro);
        return VISTA_CREAR_CENTROS;
    }

    @PostMapping(value = "/centros/solicitud/new")
    public String processCreationForm(@Valid Centro centro, BindingResult result, Map<String, Object> model, Principal principal) {
        List<String> errores = centroService.validarCentro(centro);
        if(result.hasErrors()) {
            model.put("errors", result.getAllErrors());
            return VISTA_CREAR_CENTROS;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_CREAR_CENTROS;
        } else {
            centro.setEstado(false);
            Jugador jugador = jugadorService.findJugadorByUsername(principal.getName());
		    centro.setCreador(jugador);
            centroService.saveCentro(centro);
            return VISTA_SOLICITUD_CENTRO;
        }
        
    }

    @GetMapping(value = "/centros")
    public String showCentros(Map<String, Object> model, @RequestParam(defaultValue="0") int page) {
        Page<Centro> centros = centroService.findAcceptedCentrosPageable(page);
        Integer numCentros = centros.getNumberOfElements();
        List<Centro> centrosSol = centroService.getSolicitudesCentros();
        model.put("centrosSol", centrosSol);
        model.put("centros", centros);
        model.put("numCentros", numCentros);
        return VISTA_LISTAR_CENTROS;
    }

    @GetMapping("/centros/{centroId}")
    public ModelAndView showCentroDetails(@PathVariable("centroId") int centroId, Principal principal) {
		ModelAndView mav = new ModelAndView("centros/centroDetails");
		mav.addObject("centro", this.centroService.findCentroById(centroId));
		mav.addObject("jugadorLogueado", this.jugadorService.findJugadorByUsername(principal.getName()));
		return mav;
    }

    @GetMapping(value = "/centros/solicitud/list")
    public String showSolicitudes(Map<String, Object> model) {
        List<Centro> centros = centroService.getSolicitudesCentros();
        
        model.put("centros", centros);
        return "centros/solicitudesList";
    }

    @GetMapping(value = "/centros/solicitud/accept/{centroId}")
    public String acceptSolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede aceptar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            centro.setEstado(true);
            centroService.saveCentro(centro);
            this.emailService.aceptarSolicitudEmail(centro.getCreador().getUser().getCorreo());
            return "centros/accept";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/centros/solicitud/deny/{centroId}")
    public String denySolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede denegar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            this.emailService.denegarSolicitudEmail(centro.getCreador().getUser().getCorreo());
            centro.setCreador(null);
            centroService.saveCentro(centro);
            centroService.deleteCentro(centro);
            return "centros/deny";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    //Eliminar centro para administrador
    @GetMapping(value = "/centros/delete/{centroId}")
    public String deleteCentro(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede eliminar el centro si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            centroService.deleteCentro(centro);
            return "centros/delete";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    // //Editar centro para administrador
    // @GetMapping(value = "/centros/edit/{centroId}")
    // public String initEditForm(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
    //     //Solo se puede editar el centro si el usuario es administrador
    //     Centro centro = centroService.findCentroById(centroId);
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
    //         model.put("centro", centro);
    //         return "centros/editarCentro"; 
    //     }else{
    //         model.put("message", "No tienes permisos para realizar esta acción");
    //         return "redirect:/";
    //     }
        
    // }

    // @PostMapping(value = "/centros/edit/{centroId}")
    // public String processEditForm(@Valid Centro centro, BindingResult result, @PathVariable("centroId") int centroId, Principal principal, Map<String, Object> model) {
    //     if(result.hasErrors()) {
    //         model.put("errors", result.getAllErrors());
    //         return "centros/editarCentro";
    //     }else {
    //         Centro centroToUpdate = centroService.findCentroById(centroId);
    //         BeanUtils.copyProperties(centro, centroToUpdate, "nombre", "direccion", "maps", "ciudad", "estado");
    //         this.centroService.saveCentro(centro);
    //         model.put("message", "Centro actualizado correctamente");
    //         return "redirect:/centros";
    //     }
    // }

    @GetMapping(value = "/centros/edit/{centroId}")
	public String initEditForm(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
			Centro centro = centroService.findCentroById(centroId);
			model.put("centro", centro);
			return VISTA_EDITAR_CENTROS;
			
		}else{
			return "welcome";
		}
	}
	
	
	@PostMapping(value = "/centros/edit/{centroId}")
	public String processEditForm(@Valid Centro centro, BindingResult result, @PathVariable("centroId") int centroId, Map<String, Object> model){
        List<String> errores = centroService.validarCentro(centro);
		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VISTA_EDITAR_CENTROS;
        } else if (!errores.isEmpty()) {
            model.put("errors", errores);
            return VISTA_EDITAR_CENTROS;
        } else {            Centro centroUpdate = this.centroService.findCentroById(centroId);
			BeanUtils.copyProperties(centro,centroUpdate,"nombre","ciudad","direccion","maps","estado"); 
			this.centroService.saveCentro(centro);
			return "redirect:/centros/";
		}						
		
	}
}
