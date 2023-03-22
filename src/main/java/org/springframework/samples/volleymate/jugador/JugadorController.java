package org.springframework.samples.volleymate.jugador;

import java.security.Principal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.valoracion.ValoracionService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

@Controller
public class JugadorController {
    
    private static final String VIEW_UPDATE_FORM = "jugadores/editarPerfil";
    private static final String VIEW_CREATE_FORM = "jugadores/crearJugador";
	  private static final String VIEW_NOTIFICACIONES = "jugadores/notificacionesJugador";
    private static final String HOME_TIENDA = "jugadores/tienda";
    private static final String HOME_TIENDA_VOLLEYS = "jugadores/tiendaVolleys";
    private final JugadorService jugadorService;
    private final PartidoService partidoService;
    private final SolicitudService solicitudService;
    private final ValoracionService valoracionService;

    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService, SolicitudService solicitudService,ValoracionService valoracionService ) {
		this.jugadorService = jugadorService;
    	this.partidoService = partidoService;
        this.solicitudService = solicitudService;
        this.valoracionService = valoracionService;
    }


    @GetMapping(value = "/jugadores/new")
	public String crearJugadorInicio(Map<String, Object> model) {
		Jugador jugador = new Jugador();
		model.put("jugador", jugador);
		return VIEW_CREATE_FORM;
	}


    @PostMapping(value = "/jugadores/new")
	public String processCreationForm(@Valid Jugador jugador, Map<String, Object> model) {

        List<String> errores = jugadorService.findErroresCrearJugador(jugador);

		if (!errores.isEmpty()) {
			model.put("errors", errores);
			return VIEW_CREATE_FORM;
		} else {
			User user = jugador.getUser();
            UsernamePasswordAuthenticationToken authReq= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authReq);
            this.jugadorService.saveJugador(jugador);
            return "redirect:/";
		}

		
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
			BeanUtils.copyProperties(jugador,jugadorToUpdate,"partidos","sexo","user","volleys","solicitudes","notificaciones","telephone"); 
            this.jugadorService.saveJugador(jugadorToUpdate);
			model.put("message","Jugador editado correctamente");
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
                Boolean yaValorado = valoracionService.valoracionExiste(jugadorVista.getId(), jugadorAutenticado.getId());
                
                model.put("jugadorAutenticado", jugadorAutenticado);
                model.put("jugadorVista", jugadorVista);
                model.put("id",jugadorAutenticado.getId());
                model.put("valorarId",jugadorVista.getId());
                model.put("yaValorado",yaValorado);
                
                return "jugadores/detallesJugador";
    }


    @GetMapping("/jugadores/mispartidos")
    public String showMisPartidos(Model model,
                    @PageableDefault(page = 0, size = 6) @SortDefault.SortDefaults({
        @SortDefault(sort = "id", direction = Sort.Direction.ASC), })
        Pageable pageable) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if(auth.isAuthenticated()){
				org.springframework.security.core.userdetails.User currentUser =  (org.springframework.security.core.userdetails.User) auth.getPrincipal();
				String usuario = currentUser.getUsername();
				Jugador jugador = jugadorService.findJugadorByUsername(usuario);
                Integer page = 0;
                List<Partido> arr = partidoService.getPartidosDelJugador(page, pageable, jugador);
                Comparator<Partido> comparador = Comparator.comparing(Partido::getFechaCreacion);
                List<Partido> listaOrdenada =  arr.stream().sorted(comparador.reversed()).collect(Collectors.toList());

                Integer numResults = listaOrdenada.size();
				model.addAttribute("partidos", listaOrdenada);
                model.addAttribute("pageNumber", pageable.getPageNumber());
			    model.addAttribute("hasPrevious", pageable.hasPrevious());
			    Double totalPages = Math.ceil(numResults / (pageable.getPageSize()));
			    model.addAttribute("totalPages", totalPages);

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
        String redirect = String.format("redirect:/partidos/%s", partidoId);
        if(partido == null){
            redirAttrs.addFlashAttribute("mensajeError", "Ups, parece que ha habido un problema!");
            return redirect;
        } 
        // Método servicio boolean
        Boolean yaTieneSolicitud = solicitudService.getYaTieneSolicitud(partidoId, principal);
        //////////////////////    
        if(yaTieneSolicitud){
            redirAttrs.addFlashAttribute("mensajeError", "Ya has enviado una solicitud");
            return redirect;
        } else {
            Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
            String mensaje = "";
            String value = "";
            if(jugador.getVolleys()>=partido.getPrecioPersona()){
                this.jugadorService.crearSolicitudPartido(jugador, partido);
                mensaje += "mensajeExitoso";
                value += "Solicitud enviada!";
            }else{
                mensaje += "mensajeError";
                value += "No tienes volleys suficientes. Compralos en nuestra tienda!";
            }
            redirAttrs.addFlashAttribute(mensaje, value);
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
            Jugador jugador = solicitud.getJugador();
            Partido partido = solicitud.getPartido();
            Integer volleys = partido.getPrecioPersona();
            Integer sumVolleys = jugador.getVolleys() - volleys;
            jugador.setVolleys(sumVolleys);
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
        Set<Solicitud> solicitudesRecibidas = this.solicitudService.findSolicitudesATusPartidos(jugador);
        model.put("solicitudesRecibidas", solicitudesRecibidas);
        //seccion solicitudes recibidas
        Set<Solicitud> solicitudesPendientes = this.solicitudService.findTusSolicitudes(jugador);
        model.put("solicitudesPendientes", solicitudesPendientes);
        
        return VIEW_NOTIFICACIONES;
    }

   

    @GetMapping(value="/tienda")
    public String showVistaTienda(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        model.put("jugadorRegistrado", jugador);
        return HOME_TIENDA;
    }

    @GetMapping(value="/tienda/volleys")
    public String showVistaTiendaVolleys(Principal principal, ModelMap model){
        return HOME_TIENDA_VOLLEYS;
    }

    @GetMapping(value = "/listaJugadores")
	public String buscarJugador(Model model, @PathVariable("palabraClave") String palabraClave) {
		
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	    if (auth != null){
			List<Jugador> listaJugadores = jugadorService.listAll(palabraClave);
            model.addAttribute("listaJugadores", listaJugadores);
			model.addAttribute("palabraClave", palabraClave);
			return "/listaJugadores";
		} else {
			return "redirect:/";
		}
    }

    @GetMapping(value="/tienda/volleys/comprar/{volleys}/{precio}")
    public String comprarVolleys(Principal principal, @PathVariable("volleys") Integer volleys, 
                                                        @PathVariable("volleys") Integer precio, RedirectAttributes redirAttrs){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        Integer sumVolleys = jugador.getVolleys() + volleys;
        jugador.setVolleys(sumVolleys);
        this.jugadorService.saveJugador(jugador);
        redirAttrs.addFlashAttribute("compraAceptada", "Su pago se ha procesado correctamente!");
        return HOME_TIENDA_VOLLEYS;
    }

    //Por hacer
    @GetMapping(value="/tienda/premium")
    public String showVistaTiendaSuscripcion(Principal principal, ModelMap model){
        return null;
    }
}





