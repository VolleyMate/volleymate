package org.springframework.samples.volleymate.valoracion;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Valoracion extends BaseEntity{

    @NotNull
    @Min(0)
    @Max(5)
    private int puntuacion;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Jugador ratedPlayer;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Jugador ratingPlayer;

    private String comentario;
    
}
