package org.springframework.samples.volleymate.centro;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@Controller
public class CentroController {
    public final CentroService centroService;

    private static final String VISTA_LISTAR_CENTROS = "centros/listaCentros";
    private static final String VISTA_CREAR_CENTROS = "centros/crearCentros";
    private static final String VISTA_EDITAR_CENTROS = "centros/editarCentro";
	private static final String VISTA_ELIMINAR_CENTROS = "centros/eliminarCentro";

    @Autowired
    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping(value = "/centros/solitud/new")
    public String initCreationForm(Map<String, Object> model) {
        Centro centro = new Centro();
        centro.setEstado(false);
        model.put("centro", centro);
        return VISTA_CREAR_CENTROS;
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
    public String showCentros(Map<String, Object> model, @RequestParam(defaultValue="0") int page) {
        Page<Centro> centros = centroService.findAcceptedCentrosPageable(page);
        Integer numCentros = centros.getNumberOfElements();
        model.put("centros", centros);
        model.put("numCentros", numCentros);
        return VISTA_LISTAR_CENTROS;
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

    //Eliminar centro para administrador
    @GetMapping(value = "/centros/delete/{centroId}")
    public String deleteCentro(Map<String, Object> model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede eliminar el centro si el usuario es administrador
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            centroService.deleteCentro(centroId);
            return "redirect:/centros";
        }else {
            model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
    }

    //Editar centro para administrador
    @GetMapping(value = "/centros/edit/{centroId}")
    public String initEditForm(Model model, @PathVariable("centroId") int centroId, Principal principal) {
        //Solo se puede editar el centro si el usuario es administrador
        Centro centro = centroService.findCentroById(centroId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(centro.getEstado() && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            
            String nombre = centro.getNombre();
            String direccion = centro.getDireccion();
            String ciudad = centro.getCiudad();
            String maps = centro.getMaps();
            model.addAttribute("nombre", nombre);
            model.addAttribute("direccion", direccion);
            model.addAttribute("ciudad", ciudad);
            model.addAttribute("maps", maps);

            model.addAttribute("centro", centro);
            return "centros/editCentro"; 
        }else{
            model.addAttribute("message", "No tienes permisos para realizar esta acción");
            return "redirect:/";
        }
        
    }

    @PostMapping(value = "/centros/edit/{centroId}")
    public String processEditForm(@Valid Centro centro, BindingResult result, @PathVariable("centroId") int centroId, Principal principal, Map<String, Object> model) {
        if(result.hasErrors()) {
            model.put("errors", result.getAllErrors());
            return "centros/editCentro";
        }else {
            Centro centroToUpdate = centroService.findCentroById(centroId);
            BeanUtils.copyProperties(centro, centroToUpdate, "estado");
            this.centroService.saveCentro(centro);
            model.put("message", "Centro actualizado correctamente");
            return "redirect:/centros";
        }
    }
}
