package org.springframework.samples.volleymate.mensaje;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;
import org.springframework.samples.volleymate.partido.Partido;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mensajes")
@Getter
@Setter
public class Mensaje extends BaseEntity {
    @Column(name = "contenido")
    @NotEmpty
    private String contenido;

    @Column(name = "fecha_envio", updatable = false, nullable = false)
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jugador")
    private Jugador emisor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "partido")
    private Partido partido;

}
