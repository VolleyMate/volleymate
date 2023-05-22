package org.springframework.samples.volleymate.jugador;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

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
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.samples.volleymate.valoracion.ValoracionService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.volleymate.aspecto.Aspecto;
import org.springframework.samples.volleymate.aspecto.AspectoService;

@Controller
public class JugadorController {
    
    private static final String VIEW_UPDATE_FORM = "jugadores/editarPerfil";
    private static final String VIEW_CREATE_FORM = "jugadores/crearJugador";
	private static final String VIEW_NOTIFICACIONES = "jugadores/notificacionesJugador";
    private static final String HOME_TIENDA = "pagos/tienda";
    private static final String HOME_MIS_ASPECTOS = "jugadores/listaMisAspectos";
    private static final String VIEW_LISTADO_JUGADORES = "jugadores/listaJugadores";
    private final JugadorService jugadorService;
    private final PartidoService partidoService;
    private final SolicitudService solicitudService;
    private final ValoracionService valoracionService;
    private final AspectoService aspectoService;
    private final UserService userService;
    private final AuthoritiesService authoritiesService;

    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService, SolicitudService solicitudService,ValoracionService valoracionService, AspectoService aspectoService, UserService userService, AuthoritiesService authoritiesService) {

		this.jugadorService = jugadorService;
    	this.partidoService = partidoService;
        this.solicitudService = solicitudService;
        this.valoracionService = valoracionService;
        this.aspectoService = aspectoService;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }


    @GetMapping(value = "/jugadores/new")
	public String crearJugadorInicio(Map<String, Object> model, @AuthenticationPrincipal Authentication authentication, Principal principal) {
        List<Aspecto> aspectos = this.aspectoService.findAllAspectosGratuitos();
        boolean aceptaTerminos = false;

        if (authentication != null ){
            Jugador jugadorLog = jugadorService.findJugadorByUsername(principal.getName());
            if(jugadorService.esAdmin(jugadorLog)){
                Jugador jugador = new Jugador();
                jugador.setPremium(false);
                model.put("jugador", jugador);
                model.put("aspectos", aspectos);
                model.put("aceptaTerminos", aceptaTerminos);
                return VIEW_CREATE_FORM;    
            }
            return "redirect:/";
        } else {
            Jugador jugador = new Jugador();
            jugador.setPremium(false);
            model.put("jugador", jugador);
            model.put("aspectos", aspectos);
            model.put("aceptaTerminos", aceptaTerminos);
            return VIEW_CREATE_FORM;
        }
	}


    @PostMapping(value = "/jugadores/new")
	public String processCreationForm(@Valid Jugador jugador, boolean aceptaTerminos,Map<String, Object> model, Principal principal,@AuthenticationPrincipal Authentication authentication, RedirectAttributes redirAttrs) {

        List<String> errores = jugadorService.findErroresCrearJugador(jugador);
        if(aceptaTerminos == false){
            errores.add("Debe aceptar los términos y condiciones");
        }
		if (!errores.isEmpty()) {
			model.put("errors", errores);
			return VIEW_CREATE_FORM;
		} else {
			
            User user = jugador.getUser();
            
            if (authentication != null) {
                
                Jugador jugadorLog = jugadorService.findJugadorByUsername(principal.getName());
                
                if (jugadorService.esAdmin(jugadorLog)){
                    jugador.setPremium(false);
                    this.jugadorService.saveJugador(jugador);
                    redirAttrs.addFlashAttribute("mensajeExitoso", "Jugador creado correctamente");
                    return "redirect:/jugadores/" + jugador.getId();
                } else {
                    return "redirect:/";
                }
            
            } else {
                UsernamePasswordAuthenticationToken authReq= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authReq);
                jugador.setPremium(false);
                this.jugadorService.saveJugador(jugador);
                return "redirect:/";
            }
		}

		
	}

    @GetMapping(value = "/jugadores/edit/{id}")
	public String initEditForm(Model model, @PathVariable("id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jugador jugadorAEditar = jugadorService.findJugadorById(id);
        Jugador jugadorLog = jugadorService.findJugadorByUsername(auth.getName());
		if(auth != null){
			if(jugadorService.esAdmin(jugadorLog)||jugadorAEditar.getId().equals(jugadorLog.getId())){
				try{
					
                    Jugador jugador = jugadorService.findJugadorById(id);
                    String username = jugador.getUser().getUsername();
                    String pass = jugador.getUser().getPassword();
                    Sexo sexo = jugador.getSexo();
                    model.addAttribute("pass", pass);
                    model.addAttribute("username", username);
                    model.addAttribute("sexo", sexo);
                    model.addAttribute(jugador);
                    return VIEW_UPDATE_FORM;   
                    
				} catch (DataIntegrityViolationException ex){
					
					return VIEW_UPDATE_FORM;
				}
				
			}else{
                return "redirect:/";
            }
            
		}
        return "redirect:/";
	
	}
	
	
	@PostMapping(value = "/jugadores/edit/{id}")
	public String processEditForm(@Valid Jugador jugador, BindingResult result, @PathVariable("id") int id, Map<String, Object> model, RedirectAttributes redirAttrs){
        List<String> errores = jugadorService.findErroresEditarJugador(jugador);
		if(result.hasErrors()){
			model.put("errors", result.getAllErrors());
			return VIEW_UPDATE_FORM;
        }else if(!errores.isEmpty()){
            model.put("errors", errores);
            return VIEW_UPDATE_FORM;
        } else {
			Jugador jugadorToUpdate = this.jugadorService.findJugadorById(jugador.getId());
			BeanUtils.copyProperties(jugador,jugadorToUpdate,"partidos","partidosCreados","image","sexo","fechaInicioPremium","fechaFinPremium","user","volleys","solicitudes","premium","notificaciones","telephone","aspectos"); 
            this.jugadorService.saveJugador(jugadorToUpdate);
			redirAttrs.addFlashAttribute("mensajeExitoso", "Jugador editado correctamente");
			return "redirect:/jugadores";
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
                if(jugadorAutenticado.getPremium() == true && jugadorAutenticado.getFechaFinPremium().toLocalDate().isBefore(LocalDate.now())){
                    jugadorAutenticado.setPremium(false);
                    jugadorAutenticado.setFechaInicioPremium(null);
                    jugadorAutenticado.setFechaFinPremium(null);
                    jugadorService.saveJugador(jugadorAutenticado);
                }
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugador);
                model.put("id",jugadorAutenticado.getId());
                model.put("admin",jugadorService.esAdmin(jugadorAutenticado));
                return "jugadores/detallesJugador";
            }
            return "welcome";    }

    @GetMapping("/jugadores/{jugadorId}")
    public String showJugador(@PathVariable("jugadorId") int jugadorId, Map<String,Object> model, Principal principal) {
				Jugador jugadorAutenticado = jugadorService.findJugadorByUsername(principal.getName());
                Jugador jugadorVista = jugadorService.findJugadorById(jugadorId);
                Boolean yaValorado = valoracionService.valoracionExiste(jugadorVista.getId(), jugadorAutenticado.getId());
                
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugadorVista);
                model.put("id",jugadorAutenticado.getId());
                model.put("valorarId",jugadorVista.getId());
                model.put("yaValorado",yaValorado);
                model.put("admin",jugadorService.esAdmin(jugadorAutenticado));
                
                return "jugadores/detallesJugador";
    }


    @GetMapping("/jugadores/mispartidos")
    public String showMisPartidos(Map<String,Object> model,@RequestParam(defaultValue="0") int page){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
                Page<Partido> pagePartidos = partidoService.buscarPartidosPorJugador(page, jugador);
                Integer numPartidos = pagePartidos.getNumberOfElements();
				model.put("partidos", pagePartidos);
                model.put("numPartidos", numPartidos);
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
				List<Partido> partidos = jugadorService.findPartidosByJugadorId(jugador.getId());
				model.addAttribute("partidosCreados", partidos);
				return "jugador/misPartidosCreados";
			}
    }

    @GetMapping("/jugadores/solicitudes/{partidoId}")
    public String solicitudUnirse(@PathVariable("partidoId") int partidoId, Principal principal, RedirectAttributes redirAttrs){
        Partido partido = this.partidoService.findPartidoById(partidoId);
        String redirect = String.format("redirect:/partidos/%s", partidoId);
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        if(partido == null){
            redirAttrs.addFlashAttribute("mensajeError", "Ups, ¡parece que ha habido un problema!");
            return redirect;
        }
        if((partido.getSexo().equals(org.springframework.samples.volleymate.partido.Sexo.FEMENINO) && jugador.getSexo().equals(Sexo.MASCULINO) || (partido.getSexo().equals(org.springframework.samples.volleymate.partido.Sexo.MASCULINO) && jugador.getSexo().equals(Sexo.FEMENINO)))){
            redirAttrs.addFlashAttribute("mensajeError", "No puedes unirte a un partido de sexo diferente al tuyo");
            return redirect;
        }
        // Método servicio boolean
        Boolean yaTieneSolicitud = solicitudService.getYaTieneSolicitud(partidoId, principal);
        //////////////////////    
        if(yaTieneSolicitud){
            redirAttrs.addFlashAttribute("mensajeError", "Ya has enviado una solicitud");
            return redirect;
        } else {
            
            String mensaje = "";
            String value = "";
            if(jugador.getVolleys()>=partido.getPrecioPersona()){
                this.jugadorService.crearSolicitudPartido(jugador, partido);
                mensaje += "mensajeExitoso";
                value += "¡Solicitud enviada!";
            }else{
                mensaje += "mensajeError";
                value += "No tienes volleys suficientes. ¡Cómpralos en nuestra tienda!";
                return HOME_TIENDA;
            }
            redirAttrs.addFlashAttribute(mensaje, value);
            return redirect;
        }
    }

    @GetMapping("/jugadores/solicitudes/denegar/{solicitudId}")
    public String denegarSolicitud(@PathVariable("solicitudId") int solicitudId,  RedirectAttributes redirAttrs){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        // notificar al jugador que ha sido rechazado. 
        this.jugadorService.eliminarSolicitud(solicitud);
        redirAttrs.addFlashAttribute("mensajeError", "Has rechazado la solicitud");
        redirAttrs.addFlashAttribute("jugadorSolicitud", solicitud.getJugador().getUser().getUsername());
        return "redirect:/jugadores/notificaciones";
    }
    
    @GetMapping("/jugadores/solicitudes/aceptar/{solicitudId}")
    public String aceptarSolicitud(@PathVariable("solicitudId") int solicitudId, RedirectAttributes redirAttrs){
        Solicitud solicitud = this.jugadorService.findSolicitudById(solicitudId);
        try{
                this.jugadorService.unirsePartida(solicitud.getJugador().getId(), solicitud.getPartido().getId());
                redirAttrs.addFlashAttribute("mensajeExitoso", "Has aceptado la solicitud");
                redirAttrs.addFlashAttribute("jugadorSolicitud", solicitud.getJugador().getUser().getUsername());
                Jugador jugador = solicitud.getJugador();
                Partido partido = solicitud.getPartido();
                Integer volleys = partido.getPrecioPersona();
                
                if(!jugador.getPremium()){
                    Integer sumVolleys = jugador.getVolleys() - volleys;
                    jugador.setVolleys(sumVolleys);
                }
                
                this.jugadorService.saveJugador(jugador);
                this.jugadorService.eliminarSolicitud(solicitud);
                return "redirect:/jugadores/notificaciones";
        }catch(YaUnidoException ex){
            redirAttrs.addFlashAttribute("mensajeYaEnPartido", "Ya estás unid@ a este partido");
            return "redirect:/jugadores/notificaciones";
        }
    }

    @GetMapping("/jugadores/notificaciones")
    public String showVistaNotificaciones(Principal principal, ModelMap model){
        //seccion notificaciones
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        LocalDateTime fechaActual = LocalDateTime.now();
        Set<Solicitud> solicitudesRecibidas = this.solicitudService.findSolicitudesATusPartidos(jugador);
        List<Solicitud> solicitudesNuevas = new ArrayList<>();
        for (Solicitud sol:solicitudesRecibidas){
            if (((sol.getJugador().getVolleys() - sol.getPartido().getPrecioPersona() >= 0)||(sol.getJugador().getPremium()))&&(sol.getPartido().getFecha().isAfter(fechaActual))&&(sol.getPartido().getJugadores().size()<sol.getPartido().getNumJugadoresNecesarios())){
                solicitudesNuevas.add(sol);
            }
        }
        model.put("solicitudesRecibidas", solicitudesNuevas);
        
        Set<Solicitud> solicitudesPendientes = this.solicitudService.findTusSolicitudes(jugador);
        List<Solicitud> solicitudesPendientesNuevas = new ArrayList<>();
        for (Solicitud sol:solicitudesPendientes){
            if (((sol.getJugador().getVolleys() - sol.getPartido().getPrecioPersona() >= 0)||(sol.getJugador().getPremium()))&&(sol.getPartido().getFecha().isAfter(fechaActual))&&(sol.getPartido().getJugadores().size()<sol.getPartido().getNumJugadoresNecesarios())){
                solicitudesPendientesNuevas.add(sol);
            } 
        }
        model.put("solicitudesPendientes", solicitudesPendientesNuevas);
        
        return VIEW_NOTIFICACIONES;
    }

    @RequestMapping(value = "/listaJugadores")
    public String showJugadores(Model model, @Param("palabraClave") String palabraClave,@RequestParam(defaultValue = "0") int valoracionMedia) {
               
        List<Jugador> listaJugadores = jugadorService.listAll(palabraClave, valoracionMedia);
        Integer numJugadores = listaJugadores.size();

        model.addAttribute("listaJugadores", listaJugadores);
        model.addAttribute("numJugadores", numJugadores);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("valoracionMedia", valoracionMedia);
        return VIEW_LISTADO_JUGADORES;
    }
    
    @GetMapping("/jugadores/delete/{jugadorId}")
    public String deleteJugador(Principal principal, RedirectAttributes redirAttrs, @PathVariable("jugadorId") int jugadorId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {

            Jugador jugadorLogeado = this.jugadorService.findJugadorByUsername(principal.getName());
            Jugador jugadorVista = this.jugadorService.findJugadorById(jugadorId);
            
            if(jugadorService.esAdmin(jugadorLogeado)){
                if(jugadorService.esAdmin(jugadorVista)){
                    redirAttrs.addFlashAttribute("mensajeError", "No puedes eliminar a un administrador");
                    return "redirect:/jugadores/" + jugadorId;
                }else if(jugadorId == jugadorLogeado.getId()){
                    SecurityContextHolder.getContext().setAuthentication(null);
                    Set<Authorities> authorities = new HashSet<>();

                    jugadorLogeado.getUser().setAuthorities(authorities);
                    jugadorService.deleteJugador(jugadorVista);
                    //userService.deleteUserByUsername(jugadorVista.getUser().getUsername());
                    return "redirect:/";
                }else{
                    jugadorService.deleteJugador(jugadorVista);
                    return "redirect:/listaJugadores";
                }
            }else{
                if(jugadorId == jugadorLogeado.getId()){
                    SecurityContextHolder.getContext().setAuthentication(null);
                    jugadorService.deleteJugador(jugadorVista);
                    return "redirect:/";
                }else{
                    redirAttrs.addFlashAttribute("mensajeError", "No puedes eliminar a otro jugador");
                    return "redirect:/jugadores/" + jugadorId;
                }
            }

        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value="/misAspectos")
    public String showVistaMisAspectos(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        List<Aspecto> aspectos = jugador.getAspectos();
        model.put("jugador", jugador);
        model.put("aspectos", aspectos);
        model.put("esAdmin", jugadorService.esAdmin(jugador));
        return HOME_MIS_ASPECTOS;
    }

    @GetMapping(value="/jugadores/setAspecto/{aspectoId}")
    public String setAspecto(Principal principal, ModelMap model, @PathVariable("aspectoId") Integer aspectoId){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        Aspecto aspecto = aspectoService.findById(aspectoId);
        jugador.setImage(aspecto.getImagen());
        jugadorService.saveJugador(jugador);
        return "redirect:/jugadores";
    }

    @GetMapping(value = "/terminos")
    public String showTerminos(){
        return "jugadores/terminos";
    }

    @GetMapping(value="/jugadores/volleys/add/{playerUsername}")
    public String addVolleys(Principal principal, @PathVariable("playerUsername") String playerUsername, ModelMap model){
        Jugador jugadorAñadir = this.jugadorService.findJugadorByUsername(playerUsername);
        Integer sumVolleys = jugadorAñadir.getVolleys() + 150;
        jugadorAñadir.setVolleys(sumVolleys);
        this.jugadorService.saveJugador(jugadorAñadir);
        return "redirect:/jugadores/"+jugadorAñadir.getId();        
    }

}





