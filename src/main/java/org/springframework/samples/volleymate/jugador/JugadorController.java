package org.springframework.samples.volleymate.jugador;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import java.util.Map;
import org.springframework.ui.ModelMap;
import org.dom4j.rule.Mode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoService;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudService;
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
    private static final String HOME_TIENDA = "jugadores/tienda";
    private static final String HOME_TIENDA_VOLLEYS = "jugadores/tiendaVolleys";
    private static final String HOME_TIENDA_ASPECTOS = "jugadores/tiendaAspectos";
    private static final String HOME_MIS_ASPECTOS = "jugadores/listaMisAspectos";
    private static final String HOME_TIENDA_PREMIUM = "jugadores/tiendaPremium";
    private static final String HOME_TIENDA_CONFIRMAR_COMPRA = "jugadores/confirmarCompra";
    private static final String VIEW_LISTADO_JUGADORES = "jugadores/listaJugadores";
    private final JugadorService jugadorService;
    private final PartidoService partidoService;
    private final SolicitudService solicitudService;
    private final ValoracionService valoracionService;
    private final AspectoService aspectoService;
    private final UserService userService;

    @Autowired
    public JugadorController(JugadorService jugadorService, PartidoService partidoService, SolicitudService solicitudService,
        ValoracionService valoracionService, AspectoService aspectoService, UserService userService) {
		this.jugadorService = jugadorService;
    	this.partidoService = partidoService;
        this.solicitudService = solicitudService;
        this.valoracionService = valoracionService;
        this.aspectoService = aspectoService;
        this.userService = userService;
    }


    @GetMapping(value = "/jugadores/new")
	public String crearJugadorInicio(Map<String, Object> model, @AuthenticationPrincipal Authentication authentication, Principal principal) {
		if (authentication != null ){
            Jugador jugadorLog = jugadorService.findJugadorByUsername(principal.getName());
            if(jugadorService.esAdmin(jugadorLog)){
                Jugador jugador = new Jugador();
                jugador.setPremium(false);
                model.put("jugador", jugador);
                return VIEW_CREATE_FORM;    
            }
            return "redirect:/";
        } else {
            Jugador jugador = new Jugador();
            jugador.setPremium(false);
            model.put("jugador", jugador);
            return VIEW_CREATE_FORM;
        }
	}


    @PostMapping(value = "/jugadores/new")
	public String processCreationForm(@Valid Jugador jugador, Map<String, Object> model, Principal principal,@AuthenticationPrincipal Authentication authentication) {

        List<String> errores = jugadorService.findErroresCrearJugador(jugador);

		if (!errores.isEmpty()) {
			model.put("errors", errores);
			return VIEW_CREATE_FORM;
		} else {
			
            User user = jugador.getUser();
            
            if (authentication != null) {
                
                Jugador jugadorLog = jugadorService.findJugadorByUsername(principal.getName());
                
                if (jugadorService.esAdmin(jugadorLog)){
                    this.jugadorService.saveJugador(jugador);
                    return "redirect:/jugadores/" + jugador.getId();
                } else {
                    UsernamePasswordAuthenticationToken authReq= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
                    SecurityContextHolder.getContext().setAuthentication(authReq);
                    this.jugadorService.saveJugador(jugador);
                    return "redirect:/";
                }
            
            } else {
                UsernamePasswordAuthenticationToken authReq= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authReq);
                this.jugadorService.saveJugador(jugador);
                return "redirect:/";
            }
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
            
            String mensaje = "";
            String value = "";
            if(jugador.getVolleys()>=partido.getPrecioPersona()){
                this.jugadorService.crearSolicitudPartido(jugador, partido);
                mensaje += "mensajeExitoso";
                value += "Solicitud enviada!";
            }else{
                mensaje += "mensajeError";
                value += "No tienes volleys suficientes. Compralos en nuestra tienda!";
                return HOME_TIENDA;
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
        List<Solicitud> solicitudesNuevas = new ArrayList<>();
        for (Solicitud sol:solicitudesRecibidas){
            if (sol.getJugador().getVolleys() - sol.getPartido().getPrecioPersona() >= 0){
                solicitudesNuevas.add(sol);
            } else{
                this.jugadorService.eliminarSolicitud(sol);
            }
        }
        model.put("solicitudesRecibidas", solicitudesNuevas);
        
        Set<Solicitud> solicitudesPendientes = this.solicitudService.findTusSolicitudes(jugador);
        model.put("solicitudesPendientes", solicitudesPendientes);
        
        return VIEW_NOTIFICACIONES;
    }

    @GetMapping(value="/tienda")
    public String showVistaTienda1(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        model.put("jugador", jugador);
        return HOME_TIENDA;
    }

    @GetMapping(value="/tienda/volleys")
    public String showVistaTiendaVolleys(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        model.put("jugador", jugador);
        return HOME_TIENDA_VOLLEYS;
    }

    //AÑADIR AL MODEL LOS ASPECTOS NECESARIOS PARA LA VISTA
    @GetMapping(value="/tienda/aspectos")
    public String showVistaTiendaAspectos(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        List<Aspecto> aspectos = this.aspectoService.findAllAspectos();
        Integer numAspectos = aspectos.size();
        model.put("jugador", jugador);
        model.put("aspectos", aspectos);
        model.put("numAspectos", numAspectos);
        return HOME_TIENDA_ASPECTOS;
    }

    @GetMapping(value="/tienda/premium")
    public String showVistaTiendaSuscripcion(Principal principal, ModelMap model){
        return HOME_TIENDA_PREMIUM;
    }

    @GetMapping(value="/tienda/confirmaCompra/{idCompra}")
    public String showVistaComfirmarCompra(Principal principal, @PathVariable("idCompra") Integer idCompra, Map<String,Object> model){
        switch(idCompra){
            case 1:
                model = jugadorService.getValoresCompra(7.99, 1, "paquete premium", idCompra, model);
                break;
            case 2:
                model = jugadorService.getValoresCompra(4.99, 300,"300 volleys", idCompra, model);
                break;    
            case 3:
                model = jugadorService.getValoresCompra(6.50, 450, "450 volleys", idCompra, model);
                break;
            case 4:
                model = jugadorService.getValoresCompra(14.50, 1100, "1100 volleys", idCompra, model);
                break;
            case 5:
                model = jugadorService.getValoresCompra(19.99, 1550, "1550 volleys", idCompra, model);    
                break;
            case 6:
                model = jugadorService.getValoresCompra(49.99, 4100, "4100 volleys", idCompra, model);
                break;
            case 7:
                model = jugadorService.getValoresCompra(1.00, 20,"este aspecto", idCompra, model);
                break;
        }
        return HOME_TIENDA_CONFIRMAR_COMPRA;
    }

    @GetMapping(value="/tienda/confirmaCompra")
    public String confirmarCompraPremium(Principal principal, ModelMap model){
        model.put("precio", 7.99);
        model.put("numVolleys", 0);
        model.put("paquete", "Comprar paquete premium");
        
        return HOME_TIENDA_CONFIRMAR_COMPRA;
    }

    @GetMapping(value="/tienda/volleys/comprar/{volleys}")
    public String comprarVolleys(Principal principal, @PathVariable("volleys") Integer volleys, RedirectAttributes redirAttrs, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        Integer sumVolleys = jugador.getVolleys() + volleys;
        jugador.setVolleys(sumVolleys);
        this.jugadorService.saveJugador(jugador);
        model.put("jugador", jugador);
        model.put("mensajeExito", String.format("Su compra de %s volleys ha sido realizada con éxito!", volleys));
        return HOME_TIENDA;
    }

    @GetMapping(value="/tienda/premium/comprar")
    public String comprarPremium(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        jugador.setPremium(true);
        this.jugadorService.saveJugador(jugador);
        model.put("jugador", jugador);
        model.put("mensajeExito", String.format("Ahora eres premium y disfrutas todas sus ventajas!"));
        return HOME_TIENDA;
    }

    @GetMapping(value="/tienda/aspectos/comprar/{aspectoId}")
    public String comprarAspecto(Principal principal, @PathVariable("aspectoId") int aspectoId,ModelMap model){
        Aspecto aspecto = this.aspectoService.findById(aspectoId);
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        int volleys = jugador.getVolleys();
        int precio = aspecto.getPrecio();

        if(!jugador.getPremium() && volleys>= precio){
            int volleysRestantes  = volleys - precio;
            jugador.setVolleys(volleysRestantes);
        } else if(volleys < precio){
            model.put("mensajeError", String.format("No tienes suficientes volleys!!"));
            model.put("jugador", jugador);
            return HOME_TIENDA;
        }

        jugador.getAspectos().add(aspecto);
        jugador.setAspectos(jugador.getAspectos());
        this.jugadorService.saveJugador(jugador);

        model.put("jugador", jugador);
        model.put("mensajeExito", String.format("Enhorabuena, ahora cuentas con un nuevo aspecto!"));
        return HOME_TIENDA;
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
    
    @GetMapping("/jugadores/{jugadorId}/delete")
    public String deleteJugador(Model model, @Param("clave") String clave, Principal principal, RedirectAttributes redirAttrs, @PathVariable("jugadorId") int jugadorId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
            if (clave == jugador.getUser().getPassword() && jugadorId == jugador.getId()) {
                SecurityContextHolder.getContext().setAuthentication(null);
                jugadorService.deleteJugador(jugador);
                userService.deleteUser(jugador.getUser());
                return "redirect:/";
            } else {
                redirAttrs.addFlashAttribute("claveInvalida", "La clave introducida no coincide con su contraseña");
                return "/jugadores/{jugadorId}/delete";
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
}





