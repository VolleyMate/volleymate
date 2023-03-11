package org.springframework.samples.volleymate.jugador;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JugadorController {
    
    private static final String VIEWS_JUGADOR_CREATE_OR_UPDATE_FORM = "jugadores/createOrUpdateJugadorForm";
    
    private final JugadorService jugadorService;
    private final PartidoService partidoService;

	private static final String VIEW_LISTA_PARTIDOS = "partidos/listaPartidos";


    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService) {
		this.jugadorService = jugadorService;
    	this.partidoService = partidoService;
    }

    @GetMapping("/jugadores")
    public String showJugadorAutenticado(Map<String,Object> model, Principal principal) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.isAuthenticated()){
                org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
                String usuario = currentUser.getUsername();
                Jugador jugador = jugadorService.findJugadorByUsername(usuario);
                Jugador jugadorAutenticado = jugadorService.findJugadorByUsername(principal.getName());
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugador);
                return "jugadores/detallesJugador";
            }
            return "welcome";    }

    @GetMapping("/jugadores/{jugadorId}")
    public String showJugador(@PathVariable("jugadorId") int jugadorId, Map<String,Object> model, Principal principal) {
				Jugador jugadorAutenticado = jugadorService.findJugadorByUsername(principal.getName());
                Jugador jugadorVista = jugadorService.findJugadorById(jugadorId);
                
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugadorVista);
                
                return "jugadores/detallesJugador";
    }


    @GetMapping("/jugadores/mispartidos")
    public String showMisPartidos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
				Set<Partido> partidos = jugador.getPartidos();
                List<Partido> arr = new ArrayList<>();
                arr.addAll(partidos);
                Comparator<Partido> comparador = Comparator.comparing(Partido::getFechaCreacion);
                List<Partido> listaOrdenada =  arr.stream().sorted(comparador.reversed()).collect(Collectors.toList());
				model.addAttribute("partidos", listaOrdenada);
				return "jugadores/misPartidos";
			}
			return "redirect:/";
		}
		return "redirect:/";
    }
    
    @GetMapping("/jugador/mispartidoscreados")
    public String listMisPartidosCreados(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || !auth.isAuthenticated()){
				return "redirect:/";
			}else {
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
				Set<Partido> partidos = partidoService.getPartidosByCreatorId(jugador.getId());
				model.addAttribute("partidosCreados", partidos);
				return "jugador/misPartidosCreados";
			}
    }

    @GetMapping("/jugadores/solicitudes/{partidoId}")
    public String solicitudUnirse(@PathVariable("partidoId") int partidoId, Principal principal, RedirectAttributes redirect){
        Partido partido = this.partidoService.findPartidoById(partidoId);
        if(partido != null){
            // redireccion con msg de error.
            return VIEW_LISTA_PARTIDOS;
        }        
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        this.jugadorService.crearSolicitudPartido(jugador, partido);
        // redireccion con msg de confirmacion. 
        return VIEW_LISTA_PARTIDOS;

    }


    @GetMapping(value = "/jugadores/edit/{id}")
	public String initEditForm(Model model, @PathVariable("id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String user = currentUser.getUsername();
				try{
					Jugador player = jugadorService.findJugadorByUsername(user);
					Collection<GrantedAuthority> usuario = currentUser.getAuthorities();
					for (GrantedAuthority usuarioR : usuario){
					String credencial = usuarioR.getAuthority();
						if(player.getId()==id || credencial.equals("admin")){
							Jugador jugador = jugadorService.findJugadorById(id);
							String username = jugador.getUser().getUsername();
							String pass = jugador.getUser().getPassword();

							model.addAttribute("pass", pass);
							model.addAttribute("username", username);
							
							model.addAttribute(jugador);
							return VIEWS_JUGADOR_CREATE_OR_UPDATE_FORM;
						}else{
							return "welcome";
						}
					}
				} catch (DataIntegrityViolationException ex){
					
					return VIEWS_JUGADOR_CREATE_OR_UPDATE_FORM;
				}
				
				
			}return "welcome";
		}
		return "welcome";
	
	}
	
	
	@PostMapping(value = "/jugadores/edit/{id}")
	public String processEditForm(@Valid Jugador jugador, BindingResult result, @PathVariable("id") int id, Map<String, Object> model){
		
		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VIEWS_JUGADOR_CREATE_OR_UPDATE_FORM;
		}
		else {
			Jugador jugadorToUpdate = this.jugadorService.findJugadorById(jugador.getId());
			BeanUtils.copyProperties(jugador,jugadorToUpdate,"partidos","user"); 
            this.jugadorService.saveJugador(jugadorToUpdate);
			model.put("message","Jugador editado correctamente");
			return "redirect:/jugadores";
		}						
		
	}

    @GetMapping("/jugadores/solicitudes/denegar/{solicitudId}")
    public String denegarSolicitud(@PathVariable("solicitudId") int solicitudId){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        // notificar al jugador que ha sido rechazado. 
        this.jugadorService.eliminarSolicitud(solicitud);
        return VIEW_LISTA_PARTIDOS;
    }
    
    @GetMapping("/jugadores/solicitudes/aceptar/{solicitudId}")
    public String aceptarSolicitud(@PathVariable("solicitudId") int solicitudId, RedirectAttributes redirAttrs){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        try{
            this.jugadorService.unirsePartida(solicitud.getJugador().getId(), solicitud.getPartido().getId());
            redirAttrs.addFlashAttribute("mensajeExitoso", "Enhorabuena, ya estás dentro del partido!");
            /*
                Aqui que el de frontend que redirija donde este el boton conectado a este controlador, provisionalmente redirige a partidos.
                ACORDARSE: Hay que mostrar el mensaje en la vista
            */
            return VIEW_LISTA_PARTIDOS;
           
        }catch(YaUnidoException ex){
            redirAttrs.addFlashAttribute("mensajeYaEnPartido", "Ya estás unid@ a este partido");
            /*
                Aqui que el de frontend que redirija donde este el boton conectado a este controlador, provisionalmente redirige a partidos.
                ACORDARSE: Hay que mostrar el mensaje en la vista
            */
            return VIEW_LISTA_PARTIDOS;
        }
    }

}
