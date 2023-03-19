package org.springframework.samples.volleymate.valoracion;

import javax.persistence.Entity;
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
    private Jugador ratedPlayer;

    @NotNull
    private Jugador ratingPlayer;
    
}
