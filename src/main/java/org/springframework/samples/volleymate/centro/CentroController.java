package org.springframework.samples.volleymate.centro;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class CentroController {
    public final CentroService centroService;

    @Autowired
    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping(value = "/centros/solitud/new")
    public String initCreationForm(Map<String, Object> model) {
        Centro centro = new Centro();
        centro.setEstado(false);
        model.put("centro", centro);
        return "centros/solicitudCentro";
    }

    @PostMapping(value = "/centros/solitud/new")
    public String processCreationForm(@Valid Centro centro, BindingResult result, Map<String, Object> model) {
        if(result.hasErrors()) {
            model.put("errors", result.getAllErrors());
            return "centros/solicitudCentro";
        }else {
            centroService.saveCentro(centro);
            return "redirect:/";
        }
    }

    @GetMapping(value = "/centros")
    public String showCentros(Map<String, Object> model) {
        List<Centro> centros = centroService.findAcceptedCentros();
        model.put("centros", centros);
        return "centros/centrosList";
    }

    @GetMapping(value = "/centros/solitud/list")
    public String showSolicitudes(Map<String, Object> model) {
        List<Centro> centros = centroService.getSolicitudesCentros();
        model.put("centros", centros);
        return "centros/solicitudesList";
    }

    @GetMapping(value = "/centros/solitud/accept/{centroId}")
    public String acceptSolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede aceptar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            Centro centro = centroService.findCentroById(centroId);
            centro.setEstado(true);
            centroService.saveCentro(centro);
            return "redirect:/centros/solitud/list";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/centros/solitud/deny/{centroId}")
    public String denySolicitud(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede denegar la solicitud si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            centroService.deleteCentro(centroId);
            return "redirect:/centros/solitud/list";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }
}
