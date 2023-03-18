package org.springframework.samples.volleymate.mensaje;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MensajeController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private JugadorService jugadorService;

    @GetMapping(value = "/chat/{partidoId}")
    public String showPartidos(@PathVariable("partidoId") int partidoId, Map<String, Object> model,Principal principal) {

        Partido partido = partidoService.findPartidoById(partidoId);
        Boolean estaDentro = partidoService.getJugadorDentroPartido(partidoId, principal);
        List<Mensaje> mensajes = partido.getMensajes();
        Comparator<Mensaje> comparador = Comparator.comparing(Mensaje::getFecha_envio);
        List<Mensaje> listaOrdenada = mensajes.stream().sorted(comparador).collect(Collectors.toList());

            if (estaDentro) {
                model.put("mensajes", listaOrdenada);
                model.put("partidoId", partido.getId());
                return "chat/chat";
            } else {
                return "redirect:/";
            }
    }

    @PostMapping(value = "/chat/enviar/{partidoId}")
    public String enviarMensaje(@PathVariable("partidoId") int partidoId, @RequestParam String contenidoMensaje, Principal principal,  Map<String, Object> model) {
        Mensaje mensaje = new Mensaje();
        mensaje.setContenidoMensaje(contenidoMensaje);
        mensaje.setPartido(partidoService.findPartidoById(partidoId));
        mensaje.setEmisor(jugadorService.findJugadorByUsername(principal.getName()));
        mensajeService.save(mensaje);
        return "redirect:/chat/{partidoId}";
    }
}
