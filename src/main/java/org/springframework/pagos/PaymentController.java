package org.springframework.pagos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Controller
@RequestMapping("/stripe")
public class PaymentController{

  @Autowired
  PaymentService paymentService;

  //joscasvaz
  @PostMapping("/paymentintent")
  public String payment(@RequestBody PaymentIntentDTO paymentIntentDTO, ModelMap model) throws StripeException{
    PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDTO);
    model.put("paymentIntent", paymentIntent);
    return null;
  }
  
  @PostMapping("/confirm/{id}") 
  public String confirm(@PathVariable("id") String id, ModelMap model) throws StripeException{  
    PaymentIntent paymentIntent = paymentService.confirm(id);
    model.put("paymentIntent", paymentIntent);
    return null;
  }

  @PostMapping("/cancel/{id}") 
  public String cancel(@PathVariable("id") String id, ModelMap model) throws StripeException{  
    PaymentIntent paymentIntent = paymentService.cancel(id);
    model.put("paymentIntent", paymentIntent);
    return null;
  }



}