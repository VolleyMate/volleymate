package org.springframework.pagos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentIntentDTO {
    
    public enum Currency{
        USD, EUR;
    }

    private String descripcion;

    private int cantidad;
    
    private Currency currency;


}
