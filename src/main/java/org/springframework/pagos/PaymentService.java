package org.springframework.pagos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.appengine.repackaged.com.google.api.client.util.Value;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
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

    @Autowired
    private APIContext apicontext;

    public Payment createPayment(
      Double total, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apicontext);
      }
    
}






