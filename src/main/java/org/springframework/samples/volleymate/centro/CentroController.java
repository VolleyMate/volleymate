package org.springframework.samples.volleymate.centro;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
