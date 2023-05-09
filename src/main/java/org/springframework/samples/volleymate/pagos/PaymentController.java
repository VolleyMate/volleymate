package org.springframework.samples.volleymate.pagos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.aspecto.Aspecto;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.samples.volleymate.aspecto.AspectoService;

import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {
    

    private final PaymentService paymentService;
    private final JugadorService jugadorService;
    private final AspectoService aspectoService;

    @Autowired
    public PaymentController(JugadorService jugadorService, PaymentService paymentService, AspectoService aspectoService) {
		this.jugadorService = jugadorService;
    	this.paymentService = paymentService;
        this.aspectoService = aspectoService;
    }

    private static final String HOME_TIENDA_VOLLEYS = "pagos/tiendaVolleys";
    private static final String HOME_TIENDA_ASPECTOS = "pagos/tiendaAspectos";    
    private static final String HOME_TIENDA_PREMIUM = "pagos/tiendaPremium";
    private static final String HOME_TIENDA_CONFIRMAR_COMPRA = "pagos/confirmarCompra";
    private static final String HOME_TIENDA = "pagos/tienda";


    @PostMapping("/pagos/autorizar_pago")
    public String autorizarPago(@ModelAttribute("orderDetail") OrderDetail orderDetail, BindingResult result, ModelMap model) throws PayPalRESTException{
        if(result.hasErrors()){
            model.put("errors", result.getAllErrors());
            return "jugadores/tienda";
        }
        
        String approvalLink = paymentService.authorizePayment(orderDetail);
        return "redirect:"+approvalLink;
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
        model.put("mensajeExito", String.format("¡Su compra de %s volleys ha sido realizada con éxito!", volleys));
        return HOME_TIENDA;
    }

    @GetMapping(value="/tienda/premium/comprar")
    public String comprarPremium(Principal principal, ModelMap model){
        Jugador jugador = this.jugadorService.findJugadorByUsername(principal.getName());
        jugador.setPremium(true);
        jugador.setFechaInicioPremium(LocalDateTime.now());
        jugador.setFechaFinPremium(LocalDateTime.now().plusDays(30));
        this.jugadorService.saveJugador(jugador);
        model.put("jugador", jugador);
        model.put("mensajeExito", String.format("¡Ahora eres premium y disfrutas todas sus ventajas!"));
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
            model.put("mensajeError", String.format("¡¡No tienes suficientes volleys!!"));
            model.put("jugador", jugador);
            return HOME_TIENDA;
        }

        jugador.getAspectos().add(aspecto);
        jugador.setAspectos(jugador.getAspectos());
        this.jugadorService.saveJugador(jugador);

        model.put("jugador", jugador);
        model.put("mensajeExito", String.format("Enhorabuena, ¡ahora cuentas con un nuevo aspecto!"));
        return HOME_TIENDA;
    }

}
