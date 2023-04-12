package org.springframework.pagos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import com.google.appengine.repackaged.com.google.api.client.util.Value;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {
    
    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException{
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("descripcion", paymentIntentDTO.getDescripcion());
        params.put("cantidad", paymentIntentDTO.getCantidad());
        params.put("currency", paymentIntentDTO.getCurrency());
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        return PaymentIntent.create(params);
    }

    public PaymentIntent confirm(String id) throws StripeException{
      Stripe.apiKey = secretKey;
      PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
      Map<String, Object> params = new HashMap<>();
      params.put("payment_method", "pm_card_visa");
      paymentIntent.confirm(params);
      return paymentIntent;
    }

    public PaymentIntent cancel(String id) throws StripeException{
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
      }
    
}






