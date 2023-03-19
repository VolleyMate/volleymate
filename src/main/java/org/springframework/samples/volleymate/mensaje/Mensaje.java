package org.springframework.samples.volleymate.mensaje;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    
    @Column(name = "contenido_mensaje")
    private String contenidoMensaje;

    @Column(name = "fecha_envio", updatable = false, nullable = false)
    private LocalDateTime fecha_envio = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emisor", referencedColumnName = "username")
	private Jugador emisor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "partido")
    private Partido partido;

    public String getFechaParseada(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd'/'MM'/'yyyy");
		return formato.format(fecha_envio);
    }

}
