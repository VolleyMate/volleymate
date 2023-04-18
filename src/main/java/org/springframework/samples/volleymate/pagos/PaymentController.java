package org.springframework.samples.volleymate.pagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {
    
    @Autowired
    PaymentService paymentService;

    @PostMapping("/pagos/autorizar_pago")
    public String autorizarPago(@ModelAttribute("orderDetail") OrderDetail orderDetail, BindingResult result, ModelMap model) throws PayPalRESTException{
        if(result.hasErrors()){
            model.put("errors", result.getAllErrors());
            return "jugadores/tienda";
        }
        
        String approvalLink = paymentService.authorizePayment(orderDetail);
        return "redirect:"+approvalLink;
    }

}
