package org.springframework.samples.volleymate.pagos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.stripe.model.PaymentLink.AfterCompletion.Redirect;

@Service
public class PaymentService {
    private static final String CLIENT_ID = "AWUsDwCzxB_eVyyM8KnFEwh-H35W00l-gdpZlCpX2Ql6xSFdQmRrqgNh20KTAFP3kDWHuYUEiEII_Qaw";
    private static final String CLIENT_SECRET = "EGoi0KD371fdgT7IKF3C9tpQh5bOyeUjF11qyX3DNUmN-W0RPcaaQYUCqTYZx93Jrbn8d4J69PQWOezB";

    public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException{
        try{
            Payer payer = getPayerInformation();

            //Redireccion a paypal
            RedirectUrls redirectUrls = getRedirectUrls(orderDetail.getNumVolleys());

            List<Transaction> listTransaction = getTransactionInformation(orderDetail);

            Payment requestPayment = new Payment();
            requestPayment.setTransactions(listTransaction);
            requestPayment.setRedirectUrls(redirectUrls);
            requestPayment.setPayer(payer);
            requestPayment.setIntent("authorize");

            APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");

            Payment approvedPayment = requestPayment.create(apiContext);
            System.out.println(approvedPayment);
            return getApprovalLink(approvedPayment);

        } catch(PayPalRESTException exception){
            return "/";
        }
           
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for(Links link : links){
            if(link.getRel().equalsIgnoreCase("approval_url")){
                approvalLink = link.getHref();
            }
        }
        return approvalLink;
    }

    private Payer getPayerInformation(){
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("firstName")
                    .setLastName("secondName")
                    .setLastName("correo")
                    .setEmail("email");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail){
        Details details = new Details();
        details.setSubtotal(orderDetail.getTotal());

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getDescripcion());

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private RedirectUrls getRedirectUrls(String numVolleys){
        RedirectUrls redirectUrls = new RedirectUrls();
        //Posible cambio necesario de las urls al desplegar.
        String url = String.format("http://localhost:8080/tienda/volleys/comprar/%s", numVolleys);
        redirectUrls.setCancelUrl(url);
        redirectUrls.setReturnUrl(url);

        return redirectUrls;
    }
}
