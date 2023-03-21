package org.springframework.samples.volleymate.partido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.mensaje.Mensaje;
import org.springframework.samples.volleymate.model.BaseEntity;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partidos")
@Getter
@Setter
public class Partido extends BaseEntity {

    @NotBlank
    private String nombre;

    @Enumerated(EnumType.STRING)
	@Column(name = "sexo")
    private Sexo sexo;

    @NotBlank
    private String descripcion;

    @Column(name = "tipo")
    private Tipo tipo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creador", referencedColumnName = "username")
	private Jugador creador;

    @Column(name = "num_jugadores")
    private Integer numJugadoresNecesarios;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime fecha;

    @Column(name = "precio_persona") 
	private Integer precioPersona=150;

    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    private LocalDateTime fechaCreacion=LocalDateTime.now();

    @OneToMany(mappedBy = "partido")
    private Set<Solicitud> solicitudes;

    @ManyToMany(mappedBy = "partidos")
    private List<Jugador> jugadores;

    @OneToMany(mappedBy = "partido")
    private List<Mensaje> mensajes;

    @ManyToOne
    @JoinColumn(name = "centro",referencedColumnName = "id")
    private Centro centro;

    public String getFechaParseada(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd'/'MM'/'yyyy");
		return formato.format(fecha);
    }
    
    public String getFechaCreacionParseada() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd'/'MM'/'yyyy");
		return formato.format(fechaCreacion);
	}

}