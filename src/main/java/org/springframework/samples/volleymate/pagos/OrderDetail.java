package org.springframework.samples.volleymate.pagos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderDetail {
    private String descripcion;
    private String numVolleys;
    private String total;
}
