package org.springframework.samples.volleymate.jugador;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Map;
import org.springframework.ui.ModelMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JugadorController {
    
    private static final String VIEW_UPDATE_FORM = "jugadores/editarPerfil";
    private static final String VIEW_CREATE_FORM = "jugadores/crearJugador";
	private static final String VIEW_NOTIFICACIONES = "jugadores/notificacionesJugador";
    
    private final JugadorService jugadorService;
    private final PartidoService partidoService;
    private final SolicitudService solicitudService;



    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService, SolicitudService solicitudService) {
		this.jugadorService = jugadorService;
    	this.partidoService = partidoService;
        this.solicitudService = solicitudService;
    }


    @GetMapping(value = "/jugadores/new")
	public String crearJugadorInicio(Map<String, Object> model) {
		Jugador jugador = new Jugador();
		model.put("jugador", jugador);
		return VIEW_CREATE_FORM;
	}


    @PostMapping(value = "/jugadores/new")
	public String processCreationForm(@Valid Jugador jugador, BindingResult result) {
		if (result.hasErrors()) {			
			return VIEW_CREATE_FORM;
		}
		else {
			
			User user = jugador.getUser();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<User>> violations = validator.validate(user);
			

			if(violations.isEmpty()){
				try{
					UsernamePasswordAuthenticationToken authReq= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
					SecurityContextHolder.getContext().setAuthentication(authReq);
					this.jugadorService.saveJugador(jugador);
					return "redirect:/";
				}catch (DataIntegrityViolationException ex){
					result.rejectValue("user.username", "Nombre de usuario duplicado","Este nombre de usuario ya esta en uso");
					return VIEW_CREATE_FORM;
				}
			}

			else{
				for(ConstraintViolation<User> v : violations){
					result.rejectValue("user."+ v.getPropertyPath(),v.getMessage(),v.getMessage());
				}
								
				return VIEW_CREATE_FORM;
			}
		}
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
                model.put("id",jugadorAutenticado.getId());
                return "jugadores/detallesJugador";
            }
            return "welcome";    }

    @GetMapping("/jugadores/{jugadorId}")
    public String showJugador(@PathVariable("jugadorId") int jugadorId, Map<String,Object> model, Principal principal) {
				Jugador jugadorAutenticado = jugadorService.findJugadorByUsername(principal.getName());
                Jugador jugadorVista = jugadorService.findJugadorById(jugadorId);
                
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugadorVista);
                model.put("id",jugadorAutenticado.getId());
                
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
    public String solicitudUnirse(@PathVariable("partidoId") int partidoId, Principal principal, RedirectAttributes redirAttrs){
        Partido partido = this.partidoService.findPartidoById(partidoId);
        if(partido == null){
            redirAttrs.addFlashAttribute("mensajeError", "Ups, parece que ha habido un problema!");
            String redirect = String.format("redirect:/partidos/%s", partidoId);
            return redirect;
        } 
        // Método servicio boolean
        Boolean yaTieneSolicitud = solicitudService.getYaTieneSolicitud(partidoId, principal);
        //////////////////////    
        if(yaTieneSolicitud){
            redirAttrs.addFlashAttribute("mensajeError", "Ya has enviado una solicitud");
            String redirect = String.format("redirect:/partidos/%s", partidoId);
            return redirect;
        } else {
            Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
            this.jugadorService.crearSolicitudPartido(jugador, partido);
            redirAttrs.addFlashAttribute("mensajeExitoso", "Solicitud enviada!");
            String redirect = String.format("redirect:/partidos/%s", partidoId);
            return redirect;
        }
    }

    @GetMapping("/jugadores/solicitudes/denegar/{solicitudId}")
    public String denegarSolicitud(@PathVariable("solicitudId") int solicitudId){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        // notificar al jugador que ha sido rechazado. 
        this.jugadorService.eliminarSolicitud(solicitud);
        return "redirect:/jugadores/notificaciones";
    }
    
    @GetMapping("/jugadores/solicitudes/aceptar/{solicitudId}")
    public String aceptarSolicitud(@PathVariable("solicitudId") int solicitudId, RedirectAttributes redirAttrs){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        try{
            this.jugadorService.unirsePartida(solicitud.getJugador().getId(), solicitud.getPartido().getId());
            redirAttrs.addFlashAttribute("mensajeExitoso", "Enhorabuena, ya estás dentro del partido!");
            this.jugadorService.eliminarSolicitud(solicitud);
            /*
                Aqui que el de frontend que redirija donde este el boton conectado a este controlador, provisionalmente redirige a partidos.
                ACORDARSE: Hay que mostrar el mensaje en la vista
            */
            return "redirect:/jugadores/notificaciones";
           
        }catch(YaUnidoException ex){
            redirAttrs.addFlashAttribute("mensajeYaEnPartido", "Ya estás unid@ a este partido");
            /*
                Aqui que el de frontend que redirija donde este el boton conectado a este controlador, provisionalmente redirige a partidos.
                ACORDARSE: Hay que mostrar el mensaje en la vista
            */
            return "redirect:/jugadores/notificaciones";
        }
    }

    @GetMapping("/jugadores/notificaciones")
    public String showVistaNotificaciones(Principal principal, ModelMap model){
        //seccion notificaciones
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        Set<Solicitud> solicitudesRecibidas = this.solicitudService.findSolicitudesATusPartidos(jugador);
        model.put("solicitudesRecibidas", solicitudesRecibidas);
        //seccion solicitudes recibidas
        Set<Solicitud> solicitudesPendientes = this.solicitudService.findTusSolicitudes(jugador);
        model.put("solicitudesPendientes", solicitudesPendientes);
        
        return VIEW_NOTIFICACIONES;
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
                            Sexo sexo = jugador.getSexo();

							model.addAttribute("pass", pass);
							model.addAttribute("username", username);
                            model.addAttribute("sexo", sexo);
							
							model.addAttribute(jugador);
							return VIEW_UPDATE_FORM;
						}else{
							return "welcome";
						}
					}
				} catch (DataIntegrityViolationException ex){
					
					return VIEW_UPDATE_FORM;
				}
				
				
			}return "welcome";
		}
		return "welcome";
	
	}
	
	
	@PostMapping(value = "/jugadores/edit/{id}")
	public String processEditForm(@Valid Jugador jugador, BindingResult result, @PathVariable("id") int id, Map<String, Object> model){

		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VIEW_UPDATE_FORM;
		}
		else {
			Jugador jugadorToUpdate = this.jugadorService.findJugadorById(jugador.getId());
			BeanUtils.copyProperties(jugador,jugadorToUpdate,"partidos","user","sexo"); 
            this.jugadorService.saveJugador(jugadorToUpdate);
			model.put("message","Jugador editado correctamente");
			return "redirect:/jugadores";
		}						
		
	}



}
